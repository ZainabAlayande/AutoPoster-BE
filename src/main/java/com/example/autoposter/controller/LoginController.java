package com.example.autoposter.controller;


import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {


    @GetMapping("/")
    public String home() {
        return "Welcome to LinkedIn Login!";
    }


    @GetMapping("/dashboard")
    public String dashboard(Authentication authentication) {
        OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();
        String name = oauthUser.getAttribute("localizedFirstName");
        String email = oauthUser.getAttribute("emailAddress");
        return "Hello, " + name + "! Your email is " + email;
    }




}
