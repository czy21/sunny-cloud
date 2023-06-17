package com.sunny.cloud.gateway.utils;

import org.springframework.util.StringUtils;

import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PathUtils {
    static final Pattern PATTERN = Pattern.compile("(.*?)\\/doc\\.html", Pattern.CASE_INSENSITIVE);

    public static String getContextPath(String referer) {
        if (StringUtils.hasLength(referer)) {
            try {
                URI uri = URI.create(referer);
                String path = uri.getPath();
                Matcher mather = PATTERN.matcher(path);
                if (mather.find()) {
                    return mather.group(1);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return "/";
    }
}
