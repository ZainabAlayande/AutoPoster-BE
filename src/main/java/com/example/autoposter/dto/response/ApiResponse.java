package com.example.autoposter.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class ApiResponse<T> {

    private String code;
    private boolean success;
    private String message;
    private T data;

    public ApiResponse(String code, String message, T data, boolean success) {
        this.code = code;
        this.success = success;
        this.message = message;
        this.data = data;
    }
}
