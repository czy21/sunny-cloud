package com.sunny.auth.core.model;

import lombok.Data;

@Data
public class LoginBody {
    private String username;
    private String password;
    private String validateCode;
}
