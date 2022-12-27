package com.fortna.routing.gateway.api.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.Arrays;
import java.util.stream.Collectors;


@Component
public class JWTAuthorizationFilter extends OncePerRequestFilter {

    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) {
        SecurityContextHolder.getContext().setAuthentication(getAuthentication(request));
        chain.doFilter(request, response);
    }


    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(AuthenticationConfigConstants.HEADER_STRING);
        if (token != null) {
            // parse the token.
            final var jwt = JWT.require(Algorithm.HMAC512(AuthenticationConfigConstants.SECRET.getBytes())).build().verify(token.replace(AuthenticationConfigConstants.TOKEN_PREFIX, ""));
            String user = jwt.getSubject();
            var role = jwt.getClaim("role").asArray(String.class);

            if (user != null) {
                return new UsernamePasswordAuthenticationToken(
                        user, null, Arrays.stream(role).map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
            }
        }
        return null;
    }

}
