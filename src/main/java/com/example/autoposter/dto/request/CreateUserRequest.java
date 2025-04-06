package com.example.autoposter.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class CreateUserRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String picture;

}
