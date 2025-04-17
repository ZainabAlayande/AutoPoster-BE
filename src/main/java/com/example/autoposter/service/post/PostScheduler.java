package com.example.autoposter.service.post;

import com.example.autoposter.model.Post;
import com.example.autoposter.repository.PostRepository;
import com.example.autoposter.service.linkedln.LinkedInService;
import com.example.autoposter.utils.AutoPosterUtils;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.OffsetDateTime;
import java.util.List;


public class PostScheduler {


    private PostRepository postRepository;

    private AutoPosterUtils globalUtils;


    private LinkedInService linkedInService;

    @Scheduled(fixedRate = 60000)
    public void scheduleLinkedInPosts() {
        List<Post> duePosts = postRepository.findDuePosts(OffsetDateTime.now());

        for (Post post : duePosts) {
            try {
                linkedInService.postToLinkedin(globalUtils.getLinkedinUser_AccessToken(), post.getAuthorUrn(), post.getContent());
                post.setStatus("POSTED");
                post.setPostedAt(OffsetDateTime.now().toLocalDateTime());
                postRepository.save(post);
            } catch (Exception e) {
                post.setStatus("FAILED");
                postRepository.save(post);
                e.printStackTrace();
            }
        }
    }
}
