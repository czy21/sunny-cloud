package com.sunny.auth.core.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@AllArgsConstructor
public class OAuth2Configure {

    JdbcTemplate jdbcTemplate;

//    @Bean
//    OAuth2AuthorizedClientService authorizedClientService(ClientRegistrationRepository clientRegistrationRepository) {
//        return new JdbcOAuth2AuthorizedClientService(jdbcTemplate, clientRegistrationRepository);
//    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailService();
    }

    @Bean
    SecurityFilterChain oauth2SecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(t -> {
                    t.anyRequest().authenticated();
                }
        );
        http.httpBasic(t->{

        });
        http.formLogin(t->{

        });
        return http.build();
    }

}
