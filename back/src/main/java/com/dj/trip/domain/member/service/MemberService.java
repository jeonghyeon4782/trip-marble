package com.dj.trip.domain.member.service;

import com.dj.trip.domain.member.dto.AuthenticationEmailResponseDto;
import com.dj.trip.domain.member.dto.CreateMemberRequestDto;
import com.dj.trip.domain.member.dto.FindPasswordRequestDto;
import com.dj.trip.domain.member.dto.response.GetMemberResponse;
import jakarta.servlet.http.HttpServletResponse;

public interface MemberService {
    // 회원가입
    void createMember(CreateMemberRequestDto dto) throws Exception;

    // 아이디 중복 검사
    boolean duplicateCheckMemberId(String memberId) throws Exception;

    // 닉네임 중복 검사
    boolean duplicateCheckNickname(String nickname) throws Exception;

    // 이메일 중복 검사
    AuthenticationEmailResponseDto authenticationEmail(String email) throws Exception;

    // 로그아웃
    void logout(HttpServletResponse response);

    // 아이디 찾기
    String findMemberId(String email);

    // 비밀번호 찾기
    boolean findPassword(FindPasswordRequestDto findPasswordRequestDto) throws Exception;

    GetMemberResponse getMember(String memberId);
}
