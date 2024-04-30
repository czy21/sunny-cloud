package com.sunny.auth.core.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sunny.auth.core.filter.JsonLoginAuthenticationFilter;
import com.sunny.auth.core.handler.JsonAuthenticationFailureHandler;
import com.sunny.auth.core.handler.JsonAuthenticationSuccessHandler;
import com.sunny.auth.core.model.JsonAuthenticationDetailsSource;
import com.sunny.auth.core.provider.JsonLoginAuthenticationEntryPoint;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.RequestCacheConfigurer;
import org.springframework.security.oauth2.client.JdbcOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;

@Configuration
@AllArgsConstructor
public class SecurityConfigure {

    JdbcTemplate jdbcTemplate;
    ObjectMapper objectMapper;
    JsonLoginAuthenticationEntryPoint jsonLoginAuthenticationEntryPoint;
    JsonAuthenticationDetailsSource jsonAuthenticationDetailsSource;
    JsonAuthenticationSuccessHandler jsonAuthenticationSuccessHandler;
    JsonAuthenticationFailureHandler jsonAuthenticationFailureHandler;

    @Bean
    OAuth2AuthorizedClientService authorizedClientService(ClientRegistrationRepository clientRegistrationRepository) {
        return new JdbcOAuth2AuthorizedClientService(jdbcTemplate, clientRegistrationRepository);
    }

//    @Bean
//    @Order(Ordered.HIGHEST_PRECEDENCE)
//    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
//        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
//        http.getConfigurer(OAuth2AuthorizationServerConfigurer.class).oidc(Customizer.withDefaults());
//        http.exceptionHandling(t ->
//                t.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"))
//        );
//        return http.build();
//    }

//    @Bean
//    public RegisteredClientRepository registeredClientRepository() {
//        RegisteredClient loginClient = RegisteredClient.withId(UUID.randomUUID().toString())
//                .clientId("login-client")
//                .clientSecret("{noop}openid-connect")
//                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
//                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
//                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
//                .redirectUri("http://127.0.0.1:8080/login/oauth2/code/login-client")
//                .redirectUri("http://127.0.0.1:8080/authorized")
//                .scope(OidcScopes.OPENID)
//                .scope(OidcScopes.PROFILE)
//                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
//                .build();
//        RegisteredClient registeredClient = RegisteredClient.withId(UUID.randomUUID().toString())
//                .clientId("messaging-client")
//                .clientSecret("{noop}secret")
//                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
//                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
//                .scope("message:read")
//                .scope("message:write")
//                .build();
//
//        return new InMemoryRegisteredClientRepository(loginClient, registeredClient);
//    }

    @Bean
    public SecurityFilterChain standardSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(t ->
                t.anyRequest().authenticated()
        );
        http.anonymous(AbstractHttpConfigurer::disable);
        http.sessionManagement(AbstractHttpConfigurer::disable);
        http.cors(AbstractHttpConfigurer::disable);
        http.csrf(AbstractHttpConfigurer::disable);
        http.requestCache(RequestCacheConfigurer::disable);
        http.exceptionHandling(t -> t.authenticationEntryPoint(jsonLoginAuthenticationEntryPoint));
        http.with(new JsonLoginConfigure<>(new JsonLoginAuthenticationFilter(objectMapper)), t -> {
            t.detailsSource(jsonAuthenticationDetailsSource);
            t.successHandler(jsonAuthenticationSuccessHandler);
            t.failureHandler(jsonAuthenticationFailureHandler);
            t.securityContextRepository(new DelegatingSecurityContextRepository(new RequestAttributeSecurityContextRepository(), new HttpSessionSecurityContextRepository()));
        });
        return http.build();
    }

}