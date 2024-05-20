package com.dj.trip.domain.member;

import com.dj.trip.domain.oauth.OauthServerType;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Member {
    private String memberId;
    private String password;
    private String nickname;
    private String email;
    private String imageUrl;
    private char role;
    private OauthServerType oauthServiceType;
}
