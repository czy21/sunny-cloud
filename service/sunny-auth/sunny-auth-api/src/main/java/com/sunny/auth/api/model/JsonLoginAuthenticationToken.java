/*
 * Copyright 2004, 2005, 2006 Acegi Technology Pty Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sunny.auth.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.Collection;
import java.util.Optional;

public class JsonLoginAuthenticationToken extends AbstractAuthenticationToken {
    private Object principal;
    private Object credentials;
    private Collection<GrantedAuthority> authorities;

    public JsonLoginAuthenticationToken() {
        this(null, null);
    }

    public JsonLoginAuthenticationToken(Object principal, Object credentials) {
        super(null);
        this.principal = principal;
        this.credentials = credentials;
        setAuthenticated(false);
    }

    public JsonLoginAuthenticationToken(Object principal, Object credentials, Collection<GrantedAuthority> authorities) {
        super(null);
        this.principal = principal;
        this.credentials = credentials;
        this.authorities = Optional.ofNullable(authorities).orElse(AuthorityUtils.NO_AUTHORITIES);
        super.setAuthenticated(true);
    }

    public static JsonLoginAuthenticationToken unauthenticated(Object principal, Object credentials) {
        return new JsonLoginAuthenticationToken(principal, credentials);
    }

    public static JsonLoginAuthenticationToken authenticated(Object principal, Object credentials, Collection<GrantedAuthority> authorities) {
        return new JsonLoginAuthenticationToken(principal, credentials, authorities);
    }

    @Override
    public Object getCredentials() {
        return this.credentials;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    public void setPrincipal(Object principal) {
        this.principal = principal;
    }

    public void setCredentials(Object credentials) {
        this.credentials = credentials;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Collection<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    @JsonIgnore
    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) {
        super.setAuthenticated(isAuthenticated);
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
        this.credentials = null;
    }

}
