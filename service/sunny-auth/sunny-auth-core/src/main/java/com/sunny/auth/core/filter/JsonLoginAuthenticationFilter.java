

package com.sunny.auth.core.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sunny.auth.common.model.JsonLoginAuthenticationToken;
import com.sunny.auth.core.model.LoginBody;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;

import java.io.IOException;

public class JsonLoginAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final PathPatternRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER = PathPatternRequestMatcher.withDefaults().matcher(HttpMethod.POST, "/login");

    private final ObjectMapper objectMapper;

    public JsonLoginAuthenticationFilter(ObjectMapper objectMapper) {
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER);
        this.objectMapper = objectMapper;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException {
        try (ServletInputStream inputStream = request.getInputStream()) {
            LoginBody loginBody = objectMapper.readValue(inputStream, LoginBody.class);
            JsonLoginAuthenticationToken authRequest = JsonLoginAuthenticationToken.unauthenticated(loginBody.getUsername(), loginBody);
            setDetails(request, authRequest);
            return this.getAuthenticationManager().authenticate(authRequest);
        }
    }

    protected void setDetails(HttpServletRequest request, JsonLoginAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }
}
