package com.dj.trip.domain.member.service;

import com.dj.trip.domain.member.dto.CreateMemberRequestDto;
import jakarta.servlet.http.HttpServletResponse;

public interface MemberService {
    // 회원가입
    void createMember(CreateMemberRequestDto dto) throws Exception;

    void logout(HttpServletResponse response);
}
