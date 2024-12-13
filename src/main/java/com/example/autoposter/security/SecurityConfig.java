package com.example.autoposter.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {



        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http.authorizeRequests(authorizeRequests ->
                            authorizeRequests
                                    .antMatchers("/", "/login").permitAll()
                                    .anyRequest().authenticated()
                    )
                    .oauth2Login(oauth2Login ->
                            oauth2Login
                                    .defaultSuccessUrl("/dashboard", true)
                    );
            return http.build();
        }
    }

}
