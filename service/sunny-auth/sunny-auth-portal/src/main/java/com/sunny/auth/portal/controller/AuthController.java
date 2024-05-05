package com.sunny.auth.portal.controller;

import com.sunny.auth.common.SecurityProperties;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "认证API")
@RestController
public class AuthController {

    //    @GetMapping("/haha")
//    public String index( @RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient, @AuthenticationPrincipal OAuth2User oauth2User) {
//
//        return "index";
//    }
    @Value("${sunny.auth.login-uri:}")
    private String loginUri;

    @GetMapping("/login-uri")
    public String loginUri() {
        return loginUri;
    }


    @PreAuthorize("hasAnyAuthority('list1')")
    @GetMapping("/testAuth")
    public String testAuth() {
        Authentication authentication = SecurityContextHolder.getContextHolderStrategy().getContext().getAuthentication();
        System.out.println(authentication);
        return "testAuth";
    }
}
