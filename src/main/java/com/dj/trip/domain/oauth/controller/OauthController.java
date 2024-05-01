package com.dj.trip.domain.oauth.controller;

import com.dj.trip.domain.oauth.dto.request.OauthLoginRequest;
import com.dj.trip.domain.oauth.dto.response.OauthLoginResponse;
import com.dj.trip.domain.oauth.service.OauthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class OauthController {

    private final OauthService oauthService;

    @PostMapping("/login/social")
    public OauthLoginResponse login(@RequestBody OauthLoginRequest oauthLoginRequest,
                                    HttpServletResponse response) {
        return oauthService.login(oauthLoginRequest, response);
    }


}
