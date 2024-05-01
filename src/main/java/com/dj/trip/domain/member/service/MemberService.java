package com.dj.trip.domain.member.service;

import com.dj.trip.domain.member.dto.CreateMemberRequestDto;

public interface MemberService {
    // 회원가입
    void createMember(CreateMemberRequestDto dto) throws Exception;
}
