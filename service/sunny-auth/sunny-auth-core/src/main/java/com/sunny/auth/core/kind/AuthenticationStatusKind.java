package com.sunny.auth.core.kind;

public enum AuthenticationStatusKind {
    LOGIN_FAIL(400100, "用户名或密码错误"),
    LOGIN_FAIL_SEQ(400100,"登录失败,连续登录错误次数已超过{0}次");

    private final Integer code;
    private final String message;

    AuthenticationStatusKind(Integer code, String message) {
        this.code = code;
        this.message = message;
    }


    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
