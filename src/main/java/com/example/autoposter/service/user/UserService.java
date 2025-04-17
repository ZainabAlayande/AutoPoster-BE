package com.example.autoposter.service.user;

import com.example.autoposter.dto.request.CreateUserRequest;
import com.example.autoposter.dto.response.CreateUserResponse;

import java.util.Map;

public interface UserService {

    CreateUserResponse createUser(CreateUserRequest createUserRequest, Map<String, String> map);

    String getUserLinkedin_AccessToken();
}
