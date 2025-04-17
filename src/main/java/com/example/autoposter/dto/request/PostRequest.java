package com.example.autoposter.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;


public class PostRequest {

    private String title;                   // Optional: visible headline
    private String body;                    // Optional: main body text
    private List<String> mentions;          // Optional: people/orgs to mention
    private List<String> hashtags;          // Optional: hashtags
    private String mediaUrl;// Optional: media
    private String mediaUrn;
    private String mediaType;
    private LocalDateTime scheduledTime;    // When to post

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }


    public String getMediaUrn() {
        return mediaUrn;
    }

    public void setMediaUrn(String mediaUrn) {
        this.mediaUrn = mediaUrn;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public List<String> getMentions() {
        return mentions;
    }

    public void setMentions(List<String> mentions) {
        this.mentions = mentions;
    }

    public List<String> getHashtags() {
        return hashtags;
    }

    public void setHashtags(List<String> hashtags) {
        this.hashtags = hashtags;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    public LocalDateTime getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime(LocalDateTime scheduledTime) {
        this.scheduledTime = scheduledTime;
    }
}
