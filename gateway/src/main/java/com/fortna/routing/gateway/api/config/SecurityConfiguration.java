package com.fortna.routing.gateway.api.config;

import com.fortna.routing.gateway.api.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserService authenticationUserDetailService;
    private final AuthenticationManagerBuilder auth;

    @Bean
    @SneakyThrows
    public SecurityFilterChain filterChain(HttpSecurity http) {
        var authenticationManager =
                auth.userDetailsService(authenticationUserDetailService)
                        .passwordEncoder(bCryptPasswordEncoder)
                        .and().build();
        http.cors().and().csrf().disable().authorizeHttpRequests()
                .requestMatchers(HttpMethod.POST, AuthenticationConfigConstants.SIGN_UP_URL).permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager))
                .addFilter(new JWTAuthorizationFilter(authenticationManager))
                // this disables session creation on Spring Security
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        return http.build();
    }
}
