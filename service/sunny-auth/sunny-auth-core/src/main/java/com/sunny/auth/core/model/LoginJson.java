package com.sunny.auth.core.model;

import lombok.Data;

@Data
public class LoginJson {
    private String username;
    private String password;
    private String validateCode;
}
