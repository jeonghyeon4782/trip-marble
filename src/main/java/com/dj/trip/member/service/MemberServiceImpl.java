package com.dj.trip.member.service;

import com.dj.trip.member.domain.Member;
import com.dj.trip.member.dto.CreateMemberRequestDto;
import com.dj.trip.member.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

@Service
@Log4j2
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberMapper memberMapper;
    private final BCryptPasswordEncoder encoder;
    private final ModelMapper modelMapper;

    // 회원가입
    @Override
    public void createMember(CreateMemberRequestDto dto) throws Exception {
        dto.setPassword(encoder.encode(dto.getPassword()));
        dto.setRole('U');
        memberMapper.insertMember(modelMapper.map(dto, Member.class));
    }
}
