package com.example.autoposter.dto.response;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@RequiredArgsConstructor
public class LoginResponse {

    private String id;
    private String name;
    private String email;
    private String profilePicture;
    private String accessToken;
    private long accessTokenExpiresIn;
    private String refreshToken;
    private long refreshTokenExpiresIn;

}
