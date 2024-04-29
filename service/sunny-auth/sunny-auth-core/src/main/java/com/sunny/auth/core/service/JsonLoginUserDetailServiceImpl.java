package com.sunny.auth.core.service;

import com.sunny.auth.core.model.JsonGrantedAuthority;
import com.sunny.auth.core.model.JsonUserDetails;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class JsonLoginUserDetailServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        JsonUserDetails jsonUserDetails= new JsonUserDetails();
        jsonUserDetails.setUsername("user");
        jsonUserDetails.setPassword("password");
        jsonUserDetails.setAuthorities(Set.of(new JsonGrantedAuthority("list")));
        return jsonUserDetails;
    }
}
