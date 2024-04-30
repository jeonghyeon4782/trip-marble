package com.dj.trip.member.controller;

import com.dj.trip.global.dto.ResponseDto;
import com.dj.trip.member.dto.CreateMemberRequestDto;
import com.dj.trip.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Log4j2
public class AuthController {

    private final MemberService memberService;

    // 회원가입
    @PostMapping("/register")
    public ResponseDto<String> createMember(@Valid @RequestBody CreateMemberRequestDto dto) throws Exception {
        memberService.createMember(dto);
        log.info("------------------------------------------회원가입 완료------------------------------------------");
        return new ResponseDto<>(HttpStatus.CREATED.value(), "회원가입 성공", null);
    }
}
