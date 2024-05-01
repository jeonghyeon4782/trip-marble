package com.dj.trip.domain.oauth.mapper;

import com.dj.trip.domain.oauth.OauthMember;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface OauthMemberMapper {

    OauthMember selectOauthMemberByOauthId(int oauthId);

    Optional<OauthMember> selectOauthMemberByOauthServiceTypeAndEmail(OauthMember oauthMember);

    int insertOauthMember(OauthMember oauthMember);
}
