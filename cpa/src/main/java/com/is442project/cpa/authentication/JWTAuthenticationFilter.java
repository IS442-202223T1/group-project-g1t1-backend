package com.is442project.cpa.authentication;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.is442project.cpa.account.UserAccount;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            UserAccount creds = new ObjectMapper()
                .readValue(request.getInputStream(), UserAccount.class);

            return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    creds.getUsername(),
                    creds.getPassword(),
                    new ArrayList<>())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication auth) throws IOException, ServletException {
        // System.out.println(auth.getAuthorities());
        // String[] 

        String [] roles = auth.getAuthorities().stream().map(a -> a.getAuthority()).toArray(String[]::new);

        String token = JWT.create()
            .withSubject(((User) auth.getPrincipal()).getUsername())
            .withArrayClaim("USER_ROLES", roles)
            .withExpiresAt(new Date(System.currentTimeMillis() + AuthenticationConfigConstants.EXPIRATION_TIME))
            .sign(Algorithm.HMAC512(AuthenticationConfigConstants.SECRET.getBytes()));
        response.addHeader(AuthenticationConfigConstants.HEADER_STRING, AuthenticationConfigConstants.TOKEN_PREFIX + token);
    }
}