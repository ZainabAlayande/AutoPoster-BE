package com.example.autoposter.service.user;

import com.example.autoposter.dto.request.CreateUserRequest;
import com.example.autoposter.dto.response.CreateUserResponse;
import com.example.autoposter.model.User;
import com.example.autoposter.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

import static com.example.autoposter.utils.AutoPosterUtils.getLoggedInUser;

@Service
public class UserServiceImpl implements UserService {

    final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public CreateUserResponse createUser(CreateUserRequest createUserRequest, Map<String, String> map) {
        String email = createUserRequest.getEmail().trim().toLowerCase();
        Optional<User> existingUser = userRepository.findUserByEmail(email);
        if (existingUser.isPresent()) return buildCreateUserResponse(existingUser.get());
        long accessTokenExpiresIn = Long.parseLong(map.get("expires_in"));
//        String refreshTokenExpiresIn = map.get("refresh_token_expires_in");
        LocalDateTime accessTokenExpiresAt = LocalDateTime.now().plusSeconds(accessTokenExpiresIn);
//        LocalDateTime refreshTokenExpiresAt = LocalDateTime.now().plusSeconds(Long.parseLong(refreshTokenExpiresIn));
        User user = new User.Builder().email(email).name(createUserRequest.getFirstName() + " " + createUserRequest.getLastName())
                .profilePic(createUserRequest.getPicture())
                .linkedinAccessToken(map.get("access_token"))
                .linkedinAccessTokenExpiresAt(accessTokenExpiresAt)
                .linkedinRefreshToken(map.get("refresh_token"))
                .linkedin_urn(map.get("linkedin_urn"))
//                .linkedinRefreshTokenExpiresAt(refreshTokenExpiresAt)
                .build();
        String refreshTokenExpiresInStr = map.get("refresh_token_expires_in");
        LocalDateTime refreshTokenExpiresAt = null;

        if (refreshTokenExpiresInStr != null && !refreshTokenExpiresInStr.equalsIgnoreCase("null")) {
            long refreshTokenExpiresIn = Long.parseLong(refreshTokenExpiresInStr);
            refreshTokenExpiresAt = LocalDateTime.now().plusSeconds(refreshTokenExpiresIn);
        } else {
            System.out.println("refresh_token_expires_in is missing or invalid");
            // refreshTokenExpiresAt = LocalDateTime.now().plusDays(30);
        }

        User savedUser = userRepository.save(user);
        return buildCreateUserResponse(savedUser);
    }

    @Override
    public String getUserLinkedin_AccessToken() {
        return userRepository.findUserByEmail(getLoggedInUser()).get().getLinkedinAccessToken();
    }


    private static CreateUserResponse buildCreateUserResponse(User savedUser) {
        System.out.println("saved user:: " + savedUser.toString());
        CreateUserResponse createUserResponse = new CreateUserResponse();
        createUserResponse.setId(String.valueOf(savedUser.getId()));
        createUserResponse.setEmail(savedUser.getEmail());
        createUserResponse.setName(savedUser.getName());
        createUserResponse.setProfilePicture(savedUser.getProfilePic());
        System.out.println("create user response:: " + createUserResponse.toString());
        return createUserResponse;
    }

}
