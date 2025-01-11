package com.example.autoposter.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserResponse {

    private String id;
    private String name;
    private String email;
    private String profilePicture;
}
