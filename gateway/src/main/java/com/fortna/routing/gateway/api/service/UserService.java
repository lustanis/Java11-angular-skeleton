package com.fortna.routing.gateway.api.service;

import com.fortna.routing.gateway.api.dto.UserData;
import com.fortna.routing.gateway.api.entity.User;
import com.fortna.routing.gateway.api.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public User readUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(EntityNotFoundException::new);
    }

    public void createUser(UserData userCreateRequest) {
        var user = new User();
        var byUsername = userRepository.findByUsername(userCreateRequest.username);
        if (byUsername.isPresent()) {
            throw new RuntimeException("User already registered. Please use different username.");
        }
        user.setUsername(userCreateRequest.username);
        user.setPassword(passwordEncoder.encode(userCreateRequest.password));
        userRepository.save(user);
    }

    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String username) {
        var apiUser = readUserByUsername(username);
        if (apiUser == null) {
            throw new UsernameNotFoundException(username);
        }
        return new org.springframework.security.core.userdetails.User(apiUser.getUsername(), apiUser.getPassword(), List.of());
    }
}
