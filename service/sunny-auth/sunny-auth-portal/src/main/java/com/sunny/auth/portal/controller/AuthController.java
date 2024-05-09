package com.sunny.auth.portal.controller;

import com.sunny.auth.core.AuthProperties;
import com.sunny.framework.core.model.CommonResult;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "认证API")
@RestController
public class AuthController {

    @Autowired
    AuthProperties authProperties;

    @GetMapping("/login-uri")
    public CommonResult<String> loginUri() {
        return CommonResult.ok(authProperties.getLoginUri());
    }


    @PreAuthorize("hasAnyAuthority('list1')")
    @GetMapping("/testAuth")
    public String testAuth() {
        Authentication authentication = SecurityContextHolder.getContextHolderStrategy().getContext().getAuthentication();
        System.out.println(authentication);
        return "testAuth";
    }
}
