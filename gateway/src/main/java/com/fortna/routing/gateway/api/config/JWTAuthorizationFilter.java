package com.fortna.routing.gateway.api.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.util.ArrayList;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

        public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
            super(authenticationManager);
        }

        @SneakyThrows
        @Override protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)  {
            String header = request.getHeader(AuthenticationConfigConstants.HEADER_STRING);

            if (header == null || !header.startsWith(AuthenticationConfigConstants.TOKEN_PREFIX)) {
                chain.doFilter(request, response);
                return;
            }

            var authentication = getAuthentication(request);

            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(request, response);
        }


        private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
            String token = request.getHeader(AuthenticationConfigConstants.HEADER_STRING);
            if (token != null) {
                // parse the token.
                String user = JWT.require(Algorithm.HMAC512(AuthenticationConfigConstants.SECRET.getBytes()))
                        .build()
                        .verify(token.replace(AuthenticationConfigConstants.TOKEN_PREFIX, ""))
                        .getSubject();

                if (user != null) {
                    return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
                }
                return null;
            }
            return null;
        }

    }
