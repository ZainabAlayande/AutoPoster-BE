package com.example.autoposter.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import javax.annotation.PostConstruct;

@Configuration
public class TestConfig {

    @Value("${spring.security.oauth2.client.registration.linkedin.client-secret}")
    private String clientSecret;

    @PostConstruct
    public void init() {
        System.out.println("Client Secret: " + clientSecret);
    }
}
