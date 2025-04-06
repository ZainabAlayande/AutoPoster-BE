package com.example.autoposter.dto.response;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class CreateUserResponse {

    private String id;
    private String name;
    private String email;
    private String profilePicture;

}
