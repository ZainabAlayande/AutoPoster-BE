package com.example.autoposter.service.post;

import com.example.autoposter.dto.request.PostRequest;
import com.example.autoposter.dto.response.PostResponse;
import org.springframework.stereotype.Service;

@Service
public class PostUtils {


    public static void validateRequest(PostRequest request) {

    }

    public static PostResponse buildPostResponse(Long postId) {
        PostResponse postResponse = new PostResponse();
        postResponse.setId(String.valueOf(postId));
        postResponse.setMessage("Success");
        return postResponse;
    }
}
