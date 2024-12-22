package com.example.autoposter.service.post;

import com.example.autoposter.dto.request.PostRequest;
import com.example.autoposter.dto.response.PostResponse;
import com.example.autoposter.model.Post;
import com.example.autoposter.repository.PostRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.example.autoposter.service.post.PostUtils.buildPostResponse;
import static com.example.autoposter.service.post.PostUtils.validateRequest;

@Service
public class PostService implements PostInterface {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public PostResponse schedulePost(PostRequest request) {
        validateRequest(request);
        Post savedPost = buildAndSavePost(request);
        return buildPostResponse(savedPost.getId());
    }

    public PostResponse updatePost(PostRequest request) {
        validateRequest(request);
        Post savedPost = buildAndSavePost(request);
        return buildPostResponse(savedPost.getId());
    }

    public PostResponse deletePost(PostRequest request) {
        validateRequest(request);
        Post savedPost = buildAndSavePost(request);
        return buildPostResponse(savedPost.getId());
    }

    public PostResponse getPostById(Long postId) {
        return null;
    }

    public PostResponse getAllPostForUser(String userEmail) {
        return null;
    }



    private Post savePost(Post post) {
        return postRepository.save(post);
    }

    private Post buildAndSavePost(PostRequest request) {
        Post post = new Post();
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setImageUrl(request.getImageUrl());
        post.setAuthorEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        post.setTags(request.getTags());
        post.setHashtags(request.getHashtags());
        post.setCreatedAt(LocalDateTime.now());
        post.setPostedAt(LocalDateTime.parse(request.getPostedOn()));
        return savePost(post);
    }




}
