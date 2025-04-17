package com.example.autoposter.dto.response;

import lombok.Getter;
import lombok.Setter;

//@Getter
//@Setter
public class PostResponse {

    private String id;
    private String message;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    @Override
    public String toString() {
        return "PostResponse{" +
                "id='" + id + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
