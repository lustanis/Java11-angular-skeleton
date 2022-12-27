package com.fortna.routing.gateway.api.config;

import com.fortna.routing.gateway.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserSecurityService implements UserDetailsService {

    private final UserRepository userRepository;

    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String username) {
        var apiUser = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User '" + username + "' Not Found!"));

        return new org.springframework.security.core.userdetails.User(apiUser.getUsername(), apiUser.getPassword(), List.of(
                new SimpleGrantedAuthority("authority-1"),
                new SimpleGrantedAuthority("ROLE_admin")
        ));
    }
}
