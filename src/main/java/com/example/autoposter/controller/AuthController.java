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
@RequiredArgsConstructor
public class AuthController {


    private final AuthService authService;

    @PostMapping("/linkedin")
    public ResponseEntity<ApiResponse<?>> handleLinkedInCallback(@Valid @RequestBody LinkedInAuthRequest request) {
        LoginResponse response = authService.generateLinkedInAccessToken(request);
        ApiResponse<?> apiResponse = ApiResponse.builder()
                .message("Login Successful")
                .success(true)
                .data(response)
                .build();
        return ResponseEntity.ok(apiResponse);
    }







}
