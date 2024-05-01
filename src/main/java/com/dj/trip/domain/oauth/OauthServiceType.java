package com.dj.trip.domain.oauth;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static java.util.Locale.ENGLISH;

@AllArgsConstructor
@Getter
public enum OauthServiceType {

    GOOGLE;

    @JsonCreator
    public static OauthServiceType fromName(String type) {
        return OauthServiceType.valueOf(type.toUpperCase(ENGLISH));
    }
}
