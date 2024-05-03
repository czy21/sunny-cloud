package com.sunny.auth.common.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sunny.auth.common.SecurityContext;
import com.sunny.auth.common.model.JsonLoginAuthenticationToken;
import com.sunny.framework.core.model.CommonResult;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Set;

public class SecurityUtil {

    public static PathMatcher pathMatcher = new AntPathMatcher();

    public static void writeJsonToResponse(HttpServletResponse response, CommonResult<?> result, ObjectMapper mapper) throws IOException {
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType("application/json");
        PrintWriter printWriter = response.getWriter();
        String json = mapper.writeValueAsString(result);
        printWriter.append(json);
    }

    public static boolean isIgnorePath(Set<String> patterns, String path) {
        if (patterns == null || patterns.isEmpty()) {
            return false;
        }
        return patterns.stream().anyMatch(x -> pathMatcher.match(x, path));
    }
}
