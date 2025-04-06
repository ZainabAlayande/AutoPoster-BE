package com.example.autoposter.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import javax.annotation.PostConstruct;

@Configuration
public class TestConfig {

    @Value("${spring.security.oauth2.client.registration.linkedin.client-secret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.client.registration.linkedin.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.linkedin.redirect-uri}")
    private String redirectUri;

    @Value("${spring.security.oauth2.client.provider.linkedin.token-uri}")
    private String accessTokenUrl;

    @Value("${spring.security.oauth2.client.registration.linkedin.authorization-grant-type}")
    private String grantType;

    @Value("${spring.security.oauth2.client.provider.linkedin.user-info-uri}")
    private String userInfoUri;

    @PostConstruct
    public void init() {
        System.out.println("Client Secret:: " + clientSecret);
        System.out.println("Client Id:: " + clientId);
        System.out.println("RedirectUri:: " + redirectUri);
        System.out.println("AccessTokenUrl:: " + accessTokenUrl);
        System.out.println("GrantType:: " + grantType);
        System.out.println("UserInfoUri:: " + userInfoUri);
    }
}
