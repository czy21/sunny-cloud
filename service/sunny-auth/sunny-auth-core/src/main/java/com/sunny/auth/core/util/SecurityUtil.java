package com.sunny.auth.core.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sunny.framework.core.model.CommonResult;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public class SecurityUtil {

    public static void writeJsonToResponse(HttpServletResponse response, CommonResult<?> result, ObjectMapper mapper) throws IOException {
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType("application/json");
        PrintWriter printWriter = response.getWriter();
        String json = mapper.writeValueAsString(result);
        printWriter.append(json);
    }

}
