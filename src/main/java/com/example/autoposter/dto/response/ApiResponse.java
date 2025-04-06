package com.example.autoposter.dto.response;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@ToString
public class ApiResponse<T> {

    private String code;
    private boolean success;
    private String message;
    private T data;


}
