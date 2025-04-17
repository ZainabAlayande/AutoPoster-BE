package com.example.autoposter.service.post;

import com.example.autoposter.dto.request.PostRequest;
import com.example.autoposter.dto.response.PostResponse;
import com.example.autoposter.model.Post;
import com.example.autoposter.repository.PostRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import static com.example.autoposter.service.post.PostUtils.buildPostResponse;


@Service
public class PostService implements PostInterface {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public PostResponse schedulePost(PostRequest request) {
        Post post = buildPost(request);
        String finalContent = buildPostContent(request);
        post.setContent(finalContent);
        Post savedPost = postRepository.save(post);
        return buildPostResponse(savedPost.getId());
    }

    private static Post buildPost(PostRequest request) {
        Post post = new Post();
        post.setTitle(request.getTitle());
        post.setMediaUrl(request.getMediaUrl());
        post.setMediaType(request.getMediaType());
        post.setMediaUrn(request.getMediaUrn());
        post.setScheduledTime(request.getScheduledTime());
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());
        return post;
    }


    public String buildPostContent(PostRequest request) {
        StringBuilder builder = new StringBuilder();
        if (request.getTitle() != null && !request.getTitle().isBlank()) builder.append(request.getTitle()).append("\n\n");
        if (request.getBody() != null && !request.getBody().isBlank()) builder.append(request.getBody()).append("\n\n");
        if (request.getMentions() != null && !request.getMentions().isEmpty()) {request.getMentions().forEach(m -> builder.append("@").append(m).append(" "));
            builder.append("\n");
        }
        if (request.getHashtags() != null && !request.getHashtags().isEmpty()) {
            request.getHashtags().forEach(h -> builder.append("#").append(h).append(" "));
        }
        return builder.toString().trim();
    }



    public PostResponse updatePost(PostRequest request) {
        return null;
    }

    public PostResponse deletePost(PostRequest request) {
        return null;
    }

    public PostResponse getPostById(Long postId) {
        return null;
    }

    public PostResponse getAllPostForUser(String userEmail) {
        return null;
    }





}
