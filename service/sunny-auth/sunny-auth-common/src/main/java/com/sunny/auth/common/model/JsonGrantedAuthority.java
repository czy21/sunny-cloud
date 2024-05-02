package com.sunny.auth.common.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;

public class JsonGrantedAuthority implements GrantedAuthority {

    private String role;

    public JsonGrantedAuthority() {
    }

    public JsonGrantedAuthority(String role) {
        this.role = role;
    }

    @JsonIgnore
    @Override
    public String getAuthority() {
        return this.role;
    }

    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
