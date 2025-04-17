package com.example.autoposter.service.linkedln;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class LinkedinImpl implements LinkedInService {

    @Value("${linkedin.api.share-url}")
    private String linkedinShareUrl;


    public void postToLinkedin(String accessToken, String urn, String content) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("author", urn);
        requestBody.put("lifecycleState", "PUBLISHED");

        Map<String, Object> shareContent = new HashMap<>();
        Map<String, Object> commentary = new HashMap<>();
        commentary.put("text", content);
        shareContent.put("shareCommentary", commentary);
        shareContent.put("shareMediaCategory", "NONE");

        Map<String, Object> specificContent = new HashMap<>();
        specificContent.put("com.linkedin.ugc.ShareContent", shareContent);
        requestBody.put("specificContent", specificContent);

        Map<String, Object> visibility = new HashMap<>();
        visibility.put("com.linkedin.ugc.MemberNetworkVisibility", "PUBLIC");
        requestBody.put("visibility", visibility);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-Restli-Protocol-Version", "2.0.0");
        headers.setBearerAuth(accessToken);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<String> response = restTemplate.exchange(linkedinShareUrl, HttpMethod.POST, entity, String.class);
            System.out.println("Post shared successfully: " + response.getBody());
        } catch (HttpClientErrorException e) {
            System.err.println("Error sharing post: " + e.getResponseBodyAsString());
            throw e;
        }
    }
}
