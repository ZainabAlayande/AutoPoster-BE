package com.example.autoposter.controller;


import com.example.autoposter.dto.request.CreateUserRequest;
import com.example.autoposter.dto.request.LinkedInAuthRequest;
import com.example.autoposter.dto.response.ApiResponse;
import com.example.autoposter.dto.response.CreateUserResponse;
import com.example.autoposter.dto.response.LinkedInUserInfo;
import com.example.autoposter.service.user.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/auth")
//@RequiredArgsConstructor
public class AuthController {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper;
    private final UserService userService;

    public AuthController(ObjectMapper objectMapper, UserService userService) {
        this.userService = userService;
        this.objectMapper = objectMapper;
    }

    @PostMapping("/linkedin")
    public ResponseEntity<String> handleLinkedInCallback(@RequestBody LinkedInAuthRequest request) {
        String code = request.getCode();
        String accessToken = getAccessTokenFromLinkedIn(code);
        return ResponseEntity.ok(accessToken);
    }



    private String getAccessTokenFromLinkedIn(String code) {
        System.out.println("Code => " + code);
        String accessTokenUrl = "https://www.linkedin.com/oauth/v2/accessToken";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("code", code);
        params.add("client_id", "863167ir2v99b8");
        params.add("client_secret", "WPL_AP0.BwvNFKkufj52whPP.MjAwNDUyNzcwMg==");
        params.add("redirect_uri", "http://localhost:5173/callback");

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
        ResponseEntity<Map> response = restTemplate.exchange(accessTokenUrl, HttpMethod.POST, requestEntity, Map.class);
        System.out.println("Access Token => " + Objects.requireNonNull(response.getBody()).get("access_token"));
        return (String) Objects.requireNonNull(response.getBody()).get("access_token");
    }


    @PostMapping("/user-info")
    public ResponseEntity<ApiResponse<?>> getUserInfoFromLinkedIn(@RequestBody Map<String, String> request) {
        String accessToken = request.get("accessToken");
        System.out.println("Access Token 2 => " + accessToken);

        HttpHeaders userInfoHeaders = new HttpHeaders();
        userInfoHeaders.setBearerAuth(accessToken);
        HttpEntity<String> userInfoRequest = new HttpEntity<>(userInfoHeaders);
        ResponseEntity<Map> userInfoResponse = restTemplate.exchange("https://api.linkedin.com/v2/userinfo", HttpMethod.GET, userInfoRequest, Map.class);

        LinkedInUserInfo linkedInUserInfo = getLinkedInUserInfo(userInfoResponse);
        CreateUserRequest convertedValue = objectMapper.convertValue(linkedInUserInfo, CreateUserRequest.class);
        CreateUserResponse response = userService.createUser(convertedValue);
        ApiResponse<?> apiResponse = ApiResponse.builder().code("00").success(true).message("Sign up successful").data(response).build();
        return ResponseEntity.ok(apiResponse);
    }



    private static LinkedInUserInfo getLinkedInUserInfo(ResponseEntity<Map> userInfoResponse) {
        Map<String, Object> userInfo = userInfoResponse.getBody();
        LinkedInUserInfo linkedInUserInfo = new LinkedInUserInfo();
        linkedInUserInfo.setFirstName((String) userInfo.get("given_name"));
        linkedInUserInfo.setLastName((String) userInfo.get("family_name"));
        linkedInUserInfo.setEmail((String) userInfo.get("email"));
        linkedInUserInfo.setPicture((String) userInfo.get("picture"));
        return linkedInUserInfo;
    }


}
