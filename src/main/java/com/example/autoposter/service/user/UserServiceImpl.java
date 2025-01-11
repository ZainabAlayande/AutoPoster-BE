package com.example.autoposter.service.user;

import com.example.autoposter.dto.request.CreateUserRequest;
import com.example.autoposter.dto.response.CreateUserResponse;
import com.example.autoposter.model.User;
import com.example.autoposter.repository.UserRepository;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    UserRepository userRepository;

    @Override
    public CreateUserResponse createUser(CreateUserRequest createUserRequest) {
        User user = new User();
        user.setName(createUserRequest.getName());
        user.setEmail(createUserRequest.getEmail());
        user.setProfilePic(createUserRequest.getProfilePicture());
        User savedUser = userRepository.save(user);
        return buildCreateUserResponse(savedUser);
    }

    private static CreateUserResponse buildCreateUserResponse(User savedUser) {
        CreateUserResponse createUserResponse = new CreateUserResponse();
        createUserResponse.setId(String.valueOf(savedUser.getId()));
        createUserResponse.setEmail(savedUser.getEmail());
        createUserResponse.setName(savedUser.getName());
        createUserResponse.setProfilePicture(savedUser.getProfilePic());
        return createUserResponse;
    }

}
