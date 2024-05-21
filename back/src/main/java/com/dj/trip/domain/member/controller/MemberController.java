package com.dj.trip.domain.member.controller;

import com.dj.trip.domain.member.service.MemberService;
import com.dj.trip.global.dto.ResponseDto;
import com.dj.trip.global.util.JWTUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final JWTUtil jwtUtil;

    @GetMapping
    public ResponseEntity<ResponseDto<?>> getMember(HttpServletRequest request) {
        String memberId = jwtUtil.getMemberIdByToken(request);
        return ResponseEntity.status(HttpStatus.OK.value()).body(
                new ResponseDto<>(HttpStatus.OK.value(), "회원 정보 요청 성공",
                        memberService.getMember(memberId)));
    }

    @GetMapping("{password}")
    public ResponseEntity<ResponseDto<?>> getMemberByPassword(HttpServletRequest request,
                                                              @PathVariable("password") String password
    ) {
        String memberId = jwtUtil.getMemberIdByToken(request);
        return ResponseEntity.status(HttpStatus.OK.value()).body(
                new ResponseDto<>(HttpStatus.OK.value(), "회원 수정 정보 요청 성공",
                        memberService.getMemberByPassword(memberId, password)));
    }
}
