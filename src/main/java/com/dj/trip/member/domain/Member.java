package com.dj.trip.member.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Member {
    private String memberId;
    private String password;
    private String nickname;
    private String email;
    private char role;
}
