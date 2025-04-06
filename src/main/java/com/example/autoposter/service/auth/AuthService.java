package com.example.autoposter.service.auth;

import com.example.autoposter.dto.request.LinkedInAuthRequest;
import com.example.autoposter.dto.response.LoginResponse;
import com.nimbusds.oauth2.sdk.AccessTokenResponse;
import org.springframework.web.bind.annotation.RequestBody;

public interface AuthService {

    LoginResponse generateLinkedInAccessToken(LinkedInAuthRequest request);

}
