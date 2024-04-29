package com.sunny.auth.core.service;

import com.sunny.auth.core.model.JsonGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public class JsonLoginUserDetailServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return User.withUsername(username).password("password").roles().authorities(List.of(new JsonGrantedAuthority("list"),new JsonGrantedAuthority("edit"))).build();
    }
}
