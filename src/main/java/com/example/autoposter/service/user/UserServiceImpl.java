package com.example.autoposter.service.user;

import com.example.autoposter.dto.request.CreateUserRequest;
import com.example.autoposter.dto.response.CreateUserResponse;
import com.example.autoposter.model.User;
import com.example.autoposter.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

//    @Override
//    public CreateUserResponse createUser(CreateUserRequest createUserRequest, Map<String, String> map) {
//        String email = createUserRequest.getEmail().trim().toLowerCase();
//        Optional<User> existingUser = userRepository.findUserByEmail(email);
//        if (existingUser.isPresent()) return buildCreateUserResponse(existingUser.get());
//        User user = User.builder().email(email).name(createUserRequest.getFirstName() + " " + createUserRequest.getLastName())
//                .profilePic(createUserRequest.getPicture())
//                .linkedinAccessToken(map.get("access_token")).linkedinAccessTokenExpiresAt()
//                .linkedinRefreshToken(map.get("refresh_token")).linkedinRefreshTokenExpiresAt().build();
//        User savedUser = userRepository.save(user);
//        return buildCreateUserResponse(savedUser);
//    }


    @Override
    public CreateUserResponse createUser(CreateUserRequest createUserRequest, Map<String, String> map) {
        String email = createUserRequest.getEmail().trim().toLowerCase();
        Optional<User> existingUser = userRepository.findUserByEmail(email);
        if (existingUser.isPresent()) return buildCreateUserResponse(existingUser.get());
        long accessTokenExpiresIn = Long.parseLong(map.get("expires_in"));
        long refreshTokenExpiresIn = Long.parseLong(map.get("refresh_token_expires_in"));
        LocalDateTime accessTokenExpiresAt = LocalDateTime.now().plusSeconds(accessTokenExpiresIn);
        LocalDateTime refreshTokenExpiresAt = LocalDateTime.now().plusSeconds(refreshTokenExpiresIn);
        User user = User.builder().email(email).name(createUserRequest.getFirstName() + " " + createUserRequest.getLastName())
                .profilePic(createUserRequest.getPicture())
                .linkedinAccessToken(map.get("access_token"))
                .linkedinAccessTokenExpiresAt(accessTokenExpiresAt)
                .linkedinRefreshToken(map.get("refresh_token"))
                .linkedinRefreshTokenExpiresAt(refreshTokenExpiresAt)
                .build();
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
