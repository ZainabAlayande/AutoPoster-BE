package com.example.autoposter.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

//@Getter
//@Setter
public class LinkedInAuthRequest {

    @NotBlank(message = "Authorization code is required")
    private String code;

    private String state;


    public @NotBlank(message = "Authorization code is required") String getCode() {
        return code;
    }

    public void setCode(@NotBlank(message = "Authorization code is required") String code) {
        this.code = code;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }


    @Override
    public String toString() {
        return "LinkedInAuthRequest{" +
                "code='" + code + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
