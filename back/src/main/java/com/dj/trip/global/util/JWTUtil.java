package com.dj.trip.global.util;

import com.dj.trip.global.property.JwtProperties;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
@Log4j2
@RequiredArgsConstructor
public class JWTUtil {

    private final JwtProperties jwtProperties;

    public String getAccessToken(Map<String, Object> valueMap) {
        return generateToken(valueMap, jwtProperties.getAccessExp());
    }

    public String getRefreshToken(Map<String, Object> valueMap) {
        return generateToken(valueMap, jwtProperties.getRefreshExp());
    }

    // JWT 문자열 생성
    private String generateToken(Map<String, Object> valueMap, int minute) {

        // 헤더 부분
        Map<String, Object> headers = new HashMap<>();
        headers.put("typ", "JWT");
        headers.put("alg", "HS256");

        // payload 부분
        Map<String, Object> payloads = new HashMap<>();
        payloads.putAll(valueMap);

        // 유효 기간
        int time = minute;  // 분 단위

        String jwtStr = Jwts.builder()
                .setHeader(headers)
                .setClaims(payloads)
                .setIssuedAt(Date.from(ZonedDateTime.now().toInstant()))
                .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(time).toInstant()))
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey().getBytes())
                .compact();

        return jwtStr;
    }

    // 토큰 검증
    public Map<String, Object> validateToken(String token) throws JwtException {
        Map<String, Object> claim = null;

        claim = Jwts.parser()
                .setSigningKey(jwtProperties.getSecretKey().getBytes())  // Set key
                .parseClaimsJws(token)  // 파싱 및 검증, 실패 시 에러
                .getBody();

        return claim;
    }

    // 아이디 추출
    // 토큰에서 아이디 추출
    public String getMeberId(String token) throws JwtException {
        Map<String, Object> claims = validateToken(token);
        String memberId = (String) claims.get("memberId");
        return memberId;
    }

    public void setHeaderAccessToken(HttpServletResponse response, String accessToken) {
        Cookie accessTokenCookie = new Cookie("DJTRIP_TOKEN", accessToken);
        accessTokenCookie.setMaxAge(86400);
        accessTokenCookie.setPath("/");
        accessTokenCookie.setHttpOnly(true);

        response.addCookie(accessTokenCookie);
    }

    public void setHeaderRefreshToken(HttpServletResponse response, String refreshToken) {
        Cookie refreshTokenCookie = new Cookie("DJTRIP_REFRESH_TOKEN", refreshToken);
        refreshTokenCookie.setMaxAge(604800);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setHttpOnly(true);

        response.addCookie(refreshTokenCookie);
    }
}
