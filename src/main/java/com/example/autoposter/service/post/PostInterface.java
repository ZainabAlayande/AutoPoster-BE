package com.example.autoposter.service.post;

import com.example.autoposter.dto.request.PostRequest;
import com.example.autoposter.dto.response.PostResponse;
import org.springframework.stereotype.Service;

@Service
public interface PostInterface {

    public PostResponse schedulePost(PostRequest request);

}
