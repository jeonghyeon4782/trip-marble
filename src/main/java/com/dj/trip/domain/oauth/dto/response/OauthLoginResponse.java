package com.dj.trip.domain.oauth.dto.response;

import com.dj.trip.domain.oauth.OauthMember;
import com.dj.trip.domain.oauth.OauthServiceType;

public record OauthLoginResponse(
        String email,
        OauthServiceType oauthServerType,
        Boolean isFirst
) {
    public OauthLoginResponse(OauthMember oauthMember, Boolean status) {
        this(oauthMember.getEmail(), oauthMember.getOauthServiceType(), status);
    }
}
