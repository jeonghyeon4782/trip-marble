package com.dj.trip.domain.member.controller;

import com.dj.trip.domain.mail.service.MailServiceImpl;
import com.dj.trip.domain.member.dto.*;
import com.dj.trip.domain.member.service.MemberService;
import com.dj.trip.global.dto.ResponseDto;
import jakarta.servlet.http.HttpServletResponse;
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

    private final MailServiceImpl mailService;

    // 회원가입
    @PostMapping("/register")
    public ResponseEntity<ResponseDto<String>> createMember(@Valid @RequestBody CreateMemberRequestDto dto) throws Exception {
        memberService.createMember(dto);
        log.info("------------------------------------------회원가입 완료------------------------------------------");
        return ResponseEntity.status(HttpStatus.CREATED.value())
                .body(new ResponseDto<>(HttpStatus.CREATED.value(), "회원가입 성공", null));
    }

    // 아이디 중복검사
    @PostMapping("/duplicate-check-id")
    public ResponseEntity<ResponseDto<String>> duplicateCheckMemberId(@Valid @RequestBody DuplicateCheckMemberIdRequestDto dto) throws Exception {
        if (memberService.duplicateCheckMemberId(dto.getMemberId())) {
            return ResponseEntity.status(HttpStatus.OK.value())
                    .body(new ResponseDto<>(HttpStatus.OK.value(), "사용 가능한 아이디입니다.", null));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value())
                .body(new ResponseDto<>(HttpStatus.BAD_REQUEST.value(), "중복된 아이디 입니다.", null));
    }

    // 닉네임 중복검사
    @PostMapping("/duplicate-check-nickname")
    public ResponseEntity<ResponseDto<String>> duplicateCheckNickname(@Valid @RequestBody DuplicateCheckNicknameRequestDto dto) throws Exception {
        if (memberService.duplicateCheckNickname(dto.getNickname())) {
            return ResponseEntity.status(HttpStatus.OK.value())
                    .body(new ResponseDto<>(HttpStatus.OK.value(), "사용 가능한 닉네임입니다.", null));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value())
                .body(new ResponseDto<>(HttpStatus.BAD_REQUEST.value(), "중복된 닉네임 입니다.", null));
    }

    @PostMapping("/logout")
    public ResponseEntity<ResponseDto<String>> createMember(HttpServletResponse response) throws Exception {
        memberService.logout(response);
        log.info("------------------------------------------로그아웃 완료------------------------------------------");
        return ResponseEntity.status(HttpStatus.CREATED.value())
                .body(new ResponseDto<>(HttpStatus.CREATED.value(), "로그아웃 성공", null));
    }
}

    // 이메일 인증
    @PostMapping("/authentication-email")
    public ResponseEntity<ResponseDto<AuthenticationEmailResponseDto>> authenticationEmail(@Valid @RequestBody AuthenticationEmailRequestDto authenticationEmailRequestDto) throws Exception{
        AuthenticationEmailResponseDto authenticationEmailResponseDto = memberService.authenticationEmail(authenticationEmailRequestDto.getEmail());
        if (authenticationEmailResponseDto != null) {
            return ResponseEntity.status(HttpStatus.OK.value())
                    .body(new ResponseDto<>(HttpStatus.OK.value(), "3분 이내에 인증번호를 입력해주세요.", authenticationEmailResponseDto));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value())
                .body(new ResponseDto<>(HttpStatus.BAD_REQUEST.value(), "중복된 이메일 입니다.", null));
    }
}