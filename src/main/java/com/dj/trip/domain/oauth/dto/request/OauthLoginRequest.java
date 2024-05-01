package com.dj.trip.domain.oauth.dto.request;

import com.dj.trip.domain.oauth.OauthServiceType;

public record OauthLoginRequest(
        OauthServiceType oauthServerType,
        String code
) {
}
