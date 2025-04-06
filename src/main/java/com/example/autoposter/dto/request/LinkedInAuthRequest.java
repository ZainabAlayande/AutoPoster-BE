package com.example.autoposter.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LinkedInAuthRequest {

    @NotBlank(message = "Authorization code is required")
    private String code;

    private String state;

}
