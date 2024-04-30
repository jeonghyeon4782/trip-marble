package com.dj.trip.global.security.filter;


import com.dj.trip.global.dto.ResponseDto;
import com.dj.trip.global.security.exception.RefreshTokenException;
import com.dj.trip.global.util.JWTUtil;
import com.google.gson.Gson;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

@Log4j2
@RequiredArgsConstructor
public class RefreshTokenFilter extends OncePerRequestFilter {

    private final String refreshPath;
    private final JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();

        if (!path.equals(refreshPath)) {
            log.info("------------------------------------------SKIP RefreshTokenFilter------------------------------------------");
            filterChain.doFilter(request, response);
            return;
        }

        log.info("------------------------------------------RefreshTokenFilter------------------------------------------");

        //전송된 JSON에서 accessToken과 refreshToken을 얻어온다.
        Map<String, String> tokens = parseRequestJSON(request);

        String accessToken = tokens.get("accessToken");
        String refreshToken = tokens.get("refreshToken");

        try {
            checkAccessToken(accessToken);  // 엑세스 토큰 검증
        } catch (RefreshTokenException refreshTokenException) {
            refreshTokenException.sendResponseError(response);
            return;
        }

        Map<String, Object> refreshClaims = null;

        try {
            refreshClaims = checkRefreshToken(refreshToken);
        } catch (RefreshTokenException refreshTokenException) {
            refreshTokenException.sendResponseError(response);
            return;
        }

        //Refresh Token의 유효시간이 얼마 남지 않은 경우
        Integer exp = (Integer) refreshClaims.get("exp");
        Date expTime = new Date(Instant.ofEpochMilli(exp).toEpochMilli() * 1000);
        Date current = new Date(System.currentTimeMillis());

        //만료 시간과 현재 시간의 간격 계산
        //만일 3일 미만인 경우에는 Refresh Token도 다시 생성
        long gapTime = (expTime.getTime() - current.getTime());

        String memberId = (String) refreshClaims.get("memberId");

        // 이 상태까지 오면 무조건 AccessToken은 새로 생성
        log.info("------------------------------------------새로운 Access Token 생성------------------------------------------");
        String accessTokenValue = jwtUtil.generateToken(Map.of("memberId", memberId), 30);
        String refreshTokenValue = tokens.get("refreshToken");

        //RefrshToken이 3일도 안남았다면..
        if (gapTime < (1000 * 60 * 3)) {
            //if(gapTime < (1000 * 60 * 60 * 24 * 3  ) ){
            log.info("------------------------------------------새로운 Refresh Token 생성------------------------------------------");
            refreshTokenValue = jwtUtil.generateToken(Map.of("memberId", memberId), 30 * 24 * 60);
        }

        sendTokens(accessTokenValue, refreshTokenValue, response);
    }

    private Map<String, String> parseRequestJSON(HttpServletRequest request) {

        //JSON 데이터를 분석
        try (Reader reader = new InputStreamReader(request.getInputStream())) {
            Gson gson = new Gson();
            return gson.fromJson(reader, Map.class);
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
        return null;
    }

    private void checkAccessToken(String accessToken) throws RefreshTokenException {
        try {
            jwtUtil.validateToken(accessToken);
        } catch (ExpiredJwtException expiredJwtException) {
        } catch (Exception exception) {
            throw new RefreshTokenException(RefreshTokenException.ErrorCase.NO_ACCESS);
        }
    }

    private Map<String, Object> checkRefreshToken(String refreshToken) throws RefreshTokenException {
        try {
            Map<String, Object> values = jwtUtil.validateToken(refreshToken);
            return values;
        } catch (ExpiredJwtException expiredJwtException) {
            throw new RefreshTokenException(RefreshTokenException.ErrorCase.OLD_REFRESH);
        } catch (Exception exception) {
            new RefreshTokenException(RefreshTokenException.ErrorCase.NO_REFRESH);
        }
        return null;
    }

    // 클라이언트에게 토큰 보내기
    private void sendTokens(String accessTokenValue, String refreshTokenValue, HttpServletResponse response) {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        Gson gson = new Gson();

        // ResponseDto 생성
        ResponseDto<Map<String, String>> responseDto = ResponseDto.<Map<String, String>>builder()
                .status(HttpStatus.OK.value())
                .msg("토큰 재발급 완료")
                .data(Map.of("accessToken", accessTokenValue, "refreshToken", refreshTokenValue))
                .build();

        // ResponseDto를 JSON 문자열로 변환
        String jsonStr = gson.toJson(responseDto);

        try {
            response.getWriter().println(jsonStr);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}