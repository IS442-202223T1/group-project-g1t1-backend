package com.is442project.cpa.authentication;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationConfigConstants {
    public static String SECRET;
    public static final long EXPIRATION_TIME = 864000000; // 10 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/api/v1/account/create";
    public static final String VERIFY_EMAIL_URL = "/api/v1/email/verify";

    @Value("${authentication.config.token.secret}")
    public void setDatabase(String secret) {
        SECRET = secret;
    }
}