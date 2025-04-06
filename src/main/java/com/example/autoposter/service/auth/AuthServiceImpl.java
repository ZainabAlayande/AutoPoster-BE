package com.example.autoposter.service.auth;

import com.example.autoposter.dto.request.CreateUserRequest;
import com.example.autoposter.dto.request.LinkedInAuthRequest;
import com.example.autoposter.dto.response.CreateUserResponse;
import com.example.autoposter.dto.response.LinkedInUserInfo;
import com.example.autoposter.dto.response.LoginResponse;
import com.example.autoposter.security.JwtUtil;
import com.example.autoposter.service.user.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;



@Service
public class AuthServiceImpl implements AuthService {


    @Value("${spring.security.oauth2.client.registration.linkedin.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.linkedin.client-secret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.client.registration.linkedin.redirect-uri}")
    private String redirectUri;

    @Value("${spring.security.oauth2.client.provider.linkedin.token-uri}")
    private String accessTokenUrl;

    @Value("${spring.security.oauth2.client.registration.linkedin.authorization-grant-type}")
    private String grantType;

    @Value("${spring.security.oauth2.client.provider.linkedin.user-info-uri}")
    private String userInfoUri;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final UserService userService;
    private final JwtUtil jwtUtil;

    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    public AuthServiceImpl(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }


    @Override
    public LoginResponse generateLinkedInAccessToken(LinkedInAuthRequest request) {
        Map<String, String> map = exchangeCodeForAccessToken(request.getCode());
        LinkedInUserInfo userInfo = fetchLinkedInUserProfile(map.get("access_token"));
        CreateUserRequest createUserRequest = mapToCreateUserRequest(userInfo);
        CreateUserResponse user = userService.createUser(createUserRequest, map);
        String jwtAccessToken = jwtUtil.generateAccessToken(user.getEmail());
        String jwtRefreshToken = jwtUtil.generateRefreshToken(user.getEmail());
        return buildLoginResponse(user, jwtAccessToken, jwtRefreshToken);
    }


    private Map<String, String> exchangeCodeForAccessToken(String code) {
        Map<String, String> result = new HashMap<>();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", grantType);
        params.add("code", code);
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("redirect_uri", redirectUri);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
        ResponseEntity<Map> response = restTemplate.exchange(accessTokenUrl, HttpMethod.POST, requestEntity, Map.class);

        String accessToken = (String) response.getBody().get("access_token");
        long expiresIn = (long) response.getBody().get("expires_in");
        String refreshToken = (String) response.getBody().get("refresh_token");
        long refreshTokenExpiresIn = (long) response.getBody().get("refresh_token_expires_in");

        result.put("access_token", accessToken);result.put("expires_in", String.valueOf(expiresIn));
        result.put("refresh_token", refreshToken);result.put("refresh_token_expires_in", String.valueOf(refreshTokenExpiresIn));
        return result;
    }


    private LinkedInUserInfo fetchLinkedInUserProfile(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<Map> response = restTemplate.exchange(userInfoUri, HttpMethod.GET, entity, Map.class);
        return getLinkedInUserInfo(response);
    }


    private CreateUserRequest mapToCreateUserRequest(LinkedInUserInfo userInfo) {
        return objectMapper.convertValue(userInfo, CreateUserRequest.class);
    }


    private LoginResponse buildLoginResponse(CreateUserResponse user, String accessToken, String refreshToken) {
        return LoginResponse.builder().id(user.getId()).name(user.getName()).email(user.getEmail())
                .accessToken(accessToken).refreshToken(refreshToken)
                .accessTokenExpiresIn(System.currentTimeMillis() + jwtUtil.getAccessTokenExpiry())
                .refreshTokenExpiresIn(System.currentTimeMillis() + jwtUtil.getRefreshTokenExpiry()).build();
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
