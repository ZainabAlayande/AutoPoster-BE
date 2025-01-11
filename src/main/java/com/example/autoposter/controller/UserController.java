package com.example.autoposter.controller;

import com.example.autoposter.dto.request.CreateUserRequest;
import com.example.autoposter.dto.response.ApiResponse;
import com.example.autoposter.dto.response.CreateUserResponse;
import com.example.autoposter.dto.response.LinkedInUserInfo;
import com.example.autoposter.service.user.UserService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("api/v1/user")
public class UserController {

    UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<ApiResponse<?>> registerUser(@RequestBody CreateUserRequest request) {
        CreateUserResponse response = userService.createUser(request);
        ApiResponse<?> apiResponse = ApiResponse.builder().code("00").success(true).message("Sign up successful")
                .data(response).build();
        return ResponseEntity.ok(apiResponse);
    }

}
