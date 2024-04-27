package com.sunny.auth.portal.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "认证API")
@RestController
public class AuthController  {

//    @GetMapping("/haha")
//    public String index( @RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient, @AuthenticationPrincipal OAuth2User oauth2User) {
//
//        return "index";
//    }

    @GetMapping("/test")
    public String test() {

        return "test";
    }


    @GetMapping("/testAuth")
    public String testAuth() {

        return "testAuth";
    }
}
