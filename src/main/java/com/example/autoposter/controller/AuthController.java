package com.example.autoposter.controller;


import com.example.autoposter.dto.request.LinkedInAuthRequest;
import com.example.autoposter.dto.response.ApiResponse;
import com.example.autoposter.dto.response.LoginResponse;
import com.example.autoposter.service.auth.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/auth")
public class AuthController {


    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/linkedin")
    public ResponseEntity<ApiResponse<?>> handleLinkedInCallback(@Valid @RequestBody LinkedInAuthRequest request)  {
        System.out.println("8");
        LoginResponse response = authService.generateLinkedInAccessToken(request);
        System.out.println(response.toString());
        System.out.println("9");
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse.Builder<>().message("Login Successful")
                .success(true).data(response).build());
    }










}
