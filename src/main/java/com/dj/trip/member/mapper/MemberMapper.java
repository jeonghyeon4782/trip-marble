package com.dj.trip.member.mapper;

import com.dj.trip.member.domain.Member;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {

    // 회원가입
    void insertMember(Member member);
    // 아이디로 유저 조회
    Member selectMemberByMemberId(String memberId);
}
