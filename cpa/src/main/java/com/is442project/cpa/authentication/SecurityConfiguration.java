package com.is442project.cpa.authentication;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationUserDetailService authenticationUserDetailService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeRequests().antMatchers("/h2-console/**").permitAll().and() // permit h2-console
                .authorizeRequests().antMatchers(HttpMethod.POST, AuthenticationConfigConstants.SIGN_UP_URL).permitAll().and() // permit signup URL on POST
                .authorizeRequests().antMatchers(HttpMethod.POST, AuthenticationConfigConstants.VERIFY_EMAIL_URL).permitAll().and() // permit signup URL on POST
                .authorizeRequests().antMatchers("/api/v1/**/admin/**").hasAuthority("admin").and() // permit only admin on /api/v1/admin/** */
                .authorizeRequests().antMatchers("/api/v1/**").hasAnyAuthority("borrower", "admin", "gop") // permit all 3 roles on /api/v1/**
                .anyRequest().authenticated().and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager()))
                .addFilter(new JWTAuthorizationFilter(authenticationManager()))
                // // this disables session creation on Spring Security
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.headers().frameOptions().disable();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(authenticationUserDetailService).passwordEncoder(bCryptPasswordEncoder);
    }
}