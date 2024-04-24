package com.sunny.auth.core.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.JdbcOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@AllArgsConstructor
public class OAuth2Configure {

    JdbcTemplate jdbcTemplate;

    @Bean
    OAuth2AuthorizedClientService authorizedClientService(ClientRegistrationRepository clientRegistrationRepository) {
        return new JdbcOAuth2AuthorizedClientService(jdbcTemplate, clientRegistrationRepository);
    }

    @Bean
    SecurityFilterChain oauth2SecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(t ->
                t.anyRequest().permitAll()
        );
//        http.sessionManagement(t->{
//            t.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//        });
        http.oauth2Login(t -> {
        });
        http.oauth2Client(t -> {

        });
        return http.build();
    }

}
