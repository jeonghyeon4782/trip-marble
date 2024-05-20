package com.dj.trip.domain.member.service;

import com.dj.trip.domain.member.dto.AuthenticationEmailResponseDto;
import com.dj.trip.domain.member.dto.CreateMemberRequestDto;

public interface MemberService {
    // 회원가입
    void createMember(CreateMemberRequestDto dto) throws Exception;
    // 아이디 중복 검사
    boolean duplicateCheckMemberId(String memberId) throws Exception;
    // 닉네임 중복 검사
    boolean duplicateCheckNickname(String nickname) throws Exception;
    // 이메일 중복 검사
    AuthenticationEmailResponseDto authenticationEmail(String email) throws Exception;
}
