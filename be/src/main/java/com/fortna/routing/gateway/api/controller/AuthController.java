package com.fortna.routing.gateway.api.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fortna.routing.gateway.api.config.AuthenticationConfigConstants;
import com.fortna.routing.gateway.api.dto.UserData;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;

    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticate(@RequestBody UserData request, HttpServletResponse response) {
        try {
            final var auth = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(request.username, request.password,
                            new ArrayList<>()));

            final String token = JWT.create()
                    .withSubject(request.username)
                    .withArrayClaim("role", auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).toArray(String[]::new))
                    .withExpiresAt(new Date(System.currentTimeMillis() + AuthenticationConfigConstants.EXPIRATION_TIME))
                    .sign(Algorithm.HMAC512(AuthenticationConfigConstants.SECRET.getBytes()));
            response.addHeader(AuthenticationConfigConstants.HEADER_STRING, AuthenticationConfigConstants.TOKEN_PREFIX + token);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(400).body("" + e.getMessage());
        }
    }

}