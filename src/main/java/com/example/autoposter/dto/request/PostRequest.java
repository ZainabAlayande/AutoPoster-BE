package com.example.autoposter.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class PostRequest {

    private String title;
    private String content;
    private String imageUrl;
    private List<String> tags;
    private List<String> hashtags;
    private String postedOn;

}
