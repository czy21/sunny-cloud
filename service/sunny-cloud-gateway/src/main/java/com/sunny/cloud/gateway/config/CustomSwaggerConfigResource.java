package com.sunny.cloud.gateway.config;

import com.sunny.cloud.gateway.utils.PathUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springdoc.core.properties.SwaggerUiConfigParameters;
import org.springdoc.webflux.ui.SwaggerConfigResource;
import org.springdoc.webflux.ui.SwaggerWelcomeCommon;
import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.util.*;
import java.util.stream.Collectors;

import org.springdoc.core.properties.AbstractSwaggerUiConfigProperties.SwaggerUrl;
import org.springframework.util.StringUtils;

@Slf4j
public class CustomSwaggerConfigResource extends SwaggerConfigResource {

    SwaggerWelcomeCommon swaggerWelcomeCommon;
    GatewayProperties gatewayProperties;

    public CustomSwaggerConfigResource(SwaggerWelcomeCommon swaggerWelcomeCommon,
                                       GatewayProperties gatewayProperties) {
        super(swaggerWelcomeCommon);
        this.swaggerWelcomeCommon = swaggerWelcomeCommon;
        this.gatewayProperties = gatewayProperties;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> getSwaggerUiConfig(ServerHttpRequest request) {
        Map<String, Object> resp = super.getSwaggerUiConfig(request);
        if (CollectionUtils.isEmpty((Collection<SwaggerUrl>) resp.get(SwaggerUiConfigParameters.URLS_PROPERTY))) {
            resp.put(SwaggerUiConfigParameters.URLS_PROPERTY, new LinkedHashSet<>());
        }
        LinkedHashSet<SwaggerUrl> urls = (LinkedHashSet<SwaggerUrl>) resp.get(SwaggerUiConfigParameters.URLS_PROPERTY);
        String contextPath = request.getPath().contextPath().value();
        if (!StringUtils.hasLength(contextPath)) {
            // 从header中获取
            List<String> referer = request.getHeaders().get("Referer");
            if (referer != null && !referer.isEmpty()) {
                String value = referer.get(0);
                contextPath = PathUtils.getContextPath(value);
            } else {
                contextPath = "/";
            }
        }
        log.info("contextPath: {}", contextPath);
        LinkedHashSet<Map<String, Object>> urlsOfMap = new LinkedHashSet<>();
        for (SwaggerUrl t : urls) {
            urlsOfMap.add(toMap(t.getName(), t.getDisplayName(), t.getUrl(), contextPath + "/" + t.getName()));
        }
        Set<String> existsNames = urls.stream().map(SwaggerUrl::getName).collect(Collectors.toSet());
        List<RouteDefinition> routes = Optional.ofNullable(gatewayProperties.getRoutes()).orElse(new ArrayList<>());
        for (RouteDefinition r : routes) {
            if (!existsNames.contains(r.getId())) {
                String name = Optional.ofNullable((String) r.getMetadata().get("name")).orElse(r.getId());
                urlsOfMap.add(toMap(name,name,contextPath + "/" + r.getId() + "/v3/api-docs",contextPath + "/" + r.getId()
                ));
            }
        }
        resp.put(SwaggerUiConfigParameters.URLS_PROPERTY, urlsOfMap);
        return resp;
    }

    private Map<String, Object> toMap(String name, String displayName, String url, String contextPath) {
        Map<String, Object> m = new HashMap<>();
        m.put("name", name);
        m.put("displayName", displayName);
        m.put("url", url);
        m.put("contextPath", contextPath);
        return m;
    }
}
