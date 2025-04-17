package com.example.autoposter.service.linkedln;


import org.springframework.stereotype.Service;

@Service
public interface LinkedInService {


    public void postToLinkedin(String accessToken, String urn, String content);

}
