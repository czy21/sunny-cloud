/*
 * Copyright 2002-2022 the original author or authors.
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

package com.sunny.auth.core.config;

import com.sunny.auth.core.filter.JsonLoginAuthenticationFilter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;

public final class JsonLoginConfigure<B extends HttpSecurityBuilder<B>, T extends JsonLoginConfigure<B, T, F>, F extends AbstractAuthenticationProcessingFilter> extends AbstractHttpConfigurer<T, B> {

    JsonLoginAuthenticationFilter authFilter;

    public JsonLoginConfigure(JsonLoginAuthenticationFilter filter) {
        this.authFilter = filter;
    }

    @Override
    public void configure(B http) {
        this.authFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        authFilter = postProcess(authFilter);
        http.addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
