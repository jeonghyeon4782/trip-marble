package com.dj.trip.domain.member.controller;

import com.dj.trip.domain.member.dto.request.ModifyMemberRequest;
import com.dj.trip.domain.member.service.MemberService;
import com.dj.trip.global.dto.ResponseDto;
import com.dj.trip.global.util.JWTUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @PutMapping
    public ResponseEntity<ResponseDto<?>> modifyMember(HttpServletRequest request,
                                                       @RequestPart(value = "member") ModifyMemberRequest modigyMemberRequest,
                                                       @RequestPart(value = "file", required = false) MultipartFile file
    ) {
        String memberId = jwtUtil.getMemberIdByToken(request);
        memberService.modifyMember(memberId, modigyMemberRequest, file);
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(new ResponseDto<>(HttpStatus.CREATED.value(), "리뷰 수정 완료", null));
    }
}
