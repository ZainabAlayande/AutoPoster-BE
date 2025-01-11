package com.example.autoposter.service.user;

import com.example.autoposter.dto.request.CreateUserRequest;
import com.example.autoposter.dto.response.CreateUserResponse;

public interface UserService {

    CreateUserResponse createUser(CreateUserRequest createUserRequest);

}
