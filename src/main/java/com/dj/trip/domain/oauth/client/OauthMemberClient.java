package com.dj.trip.domain.oauth.client;

import com.dj.trip.domain.oauth.OauthMember;
import com.dj.trip.domain.oauth.OauthServiceType;

public interface OauthMemberClient {
    OauthServiceType supportServer();

    OauthMember fetch(String code);
}
