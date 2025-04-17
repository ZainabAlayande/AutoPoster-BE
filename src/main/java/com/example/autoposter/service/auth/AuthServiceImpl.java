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
        System.out.println("1");
        Map<String, String> map = exchangeCodeForAccessToken(request.getCode());
        System.out.println("2");
        LinkedInUserInfo userInfo = fetchLinkedInUserProfile(map.get("access_token"));
        System.out.println("3");
        CreateUserRequest createUserRequest = mapToCreateUserRequest(userInfo);
        System.out.println("4");
        CreateUserResponse user = userService.createUser(createUserRequest, map);
        System.out.println("5");
        String jwtAccessToken = jwtUtil.generateAccessToken(user.getEmail());
        System.out.println("6");
        String jwtRefreshToken = jwtUtil.generateRefreshToken(user.getEmail());
        System.out.println("7");
        return buildLoginResponse(user, jwtAccessToken, jwtRefreshToken);
    }


    private Map<String, String> exchangeCodeForAccessToken(String code) {
        System.out.println("a");
        Map<String, String> result = new HashMap<>();
        System.out.println("b");
        HttpHeaders headers = new HttpHeaders();
        System.out.println("c");
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        System.out.println("d");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        System.out.println("e");
        params.add("grant_type", grantType);
        System.out.println("af");
        params.add("code", code);
        System.out.println("g");
        params.add("client_id", clientId);
        System.out.println("h");
        params.add("client_secret", clientSecret);
        System.out.println("i");
        params.add("redirect_uri", redirectUri);
        System.out.println("j");
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
        System.out.println("k");

        ResponseEntity<Map> response = restTemplate.exchange(accessTokenUrl, HttpMethod.POST, requestEntity, Map.class);
        System.out.println("l");
        System.out.println("response.getBody():: " + response.getBody());

        String accessToken = (String) response.getBody().get("access_token");
        System.out.println("m");
        Integer expiresIn = (Integer) response.getBody().get("expires_in");
        System.out.println("n");
        String refreshToken = (String) response.getBody().get("refresh_token");
        String linkedin_urn = (String) response.getBody().get("sub");
        System.out.println("linkedin_urn:: " + linkedin_urn);
        System.out.println("o");

        Integer refreshTokenExpiresIn = (Integer) response.getBody().get("refresh_token_expires_in");
        System.out.println("p");

        result.put("linkedin_urn", linkedin_urn);
        result.put("access_token", accessToken);
        result.put("expires_in", String.valueOf(expiresIn));
        System.out.println("q");

        result.put("refresh_token", refreshToken);result.put("refresh_token_expires_in", String.valueOf(refreshTokenExpiresIn));
        System.out.println("r");

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
        return new LoginResponse.Builder().id(user.getId()).name(user.getName()).email(user.getEmail())
                .profilePicture(user.getProfilePicture())
                .accessToken(accessToken).refreshToken(refreshToken)
                .accessTokenExpiresIn(System.currentTimeMillis() + 600L)
                .refreshTokenExpiresIn(System.currentTimeMillis() + 900L).build();
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
