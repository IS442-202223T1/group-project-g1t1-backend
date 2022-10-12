package com.is442project.cpa.config;

public class AuthenticationConfigConstants {
    public static final String SECRET = AuthenticationSecretGenerator.generateSecret();
    public static final long EXPIRATION_TIME = 864000000; // 10 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/api/v1/admin/create";
}