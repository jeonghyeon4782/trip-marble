package com.dj.trip.domain.member.controller;

import com.dj.trip.domain.member.dto.CreateMemberRequestDto;
import com.dj.trip.domain.member.service.MemberService;
import com.dj.trip.global.dto.ResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ResponseDto<String>> createMember(@Valid @RequestBody CreateMemberRequestDto dto) throws Exception {
        memberService.createMember(dto);
        log.info("------------------------------------------회원가입 완료------------------------------------------");
        return ResponseEntity.status(HttpStatus.CREATED.value())
                .body(new ResponseDto<>(HttpStatus.CREATED.value(), "회원가입 성공", null));
    }
}
