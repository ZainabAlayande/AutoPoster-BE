package com.example.autoposter.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);

        http.authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/login", "/login-successful").permitAll() // Allow public access to these endpoints
                        .anyRequest().authenticated() // Secure all other endpoints
                )
                .oauth2Login(oauth2 -> oauth2
                        .defaultSuccessUrl("/dashboard", true) // Redirect to /dashboard after successful login
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/") // Redirect to home page after logout
                        .permitAll()
                );

        return http.build();
    }
}
