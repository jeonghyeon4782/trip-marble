package com.dj.trip.member.service;

import com.dj.trip.member.dto.CreateMemberRequestDto;
import org.springframework.dao.DuplicateKeyException;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

public interface MemberService {
    // 회원가입
    void createMember(CreateMemberRequestDto dto) throws Exception;
}
