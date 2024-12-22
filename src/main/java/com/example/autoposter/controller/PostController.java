package com.example.autoposter.controller;

import com.example.autoposter.dto.request.PostRequest;
import com.example.autoposter.dto.response.ApiResponse;
import com.example.autoposter.dto.response.PostResponse;
import com.example.autoposter.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/post")
public class PostController {


    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/schedule")
    public ResponseEntity<ApiResponse<?>> schedulePost(PostRequest postRequest) {
        PostResponse response = postService.schedulePost(postRequest);
        ApiResponse<?> apiResponse = ApiResponse.builder().code("00").message("").success(true).data(response).build();
        return ResponseEntity.ok().body(apiResponse);
    }

    @PutMapping("/edit")
    public ResponseEntity<ApiResponse<?>> updatePost(PostRequest postRequest) {
        PostResponse response = postService.schedulePost(postRequest);
        ApiResponse<?> apiResponse = ApiResponse.builder().code("00").message("").success(true).data(response).build();
        return ResponseEntity.ok().body(apiResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> getPostByPostId(PostRequest postRequest, @PathVariable String id) {
        PostResponse response = postService.schedulePost(postRequest);
        ApiResponse<?> apiResponse = ApiResponse.builder().code("00").message("").success(true).data(response).build();
        return ResponseEntity.ok().body(apiResponse);
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<?>> getAllPostForUser(PostRequest postRequest) {
        PostResponse response = postService.schedulePost(postRequest);
        ApiResponse<?> apiResponse = ApiResponse.builder().code("00").message("").success(true).data(response).build();
        return ResponseEntity.ok().body(apiResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> deletePostById(PostRequest postRequest, @PathVariable String id) {
        PostResponse response = postService.schedulePost(postRequest);
        ApiResponse<?> apiResponse = ApiResponse.builder().code("00").message("").success(true).data(response).build();
        return ResponseEntity.ok().body(apiResponse);
    }


}
