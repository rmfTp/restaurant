package org.koreait.member.social.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@SpringBootTest
public class AccessTokenTest {
    @Autowired
    private RestTemplate restTemplate;

    @Test
    void test() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", "554171db1e052c89471da8f35fb9e6ac");
        body.add("redirect_uri", "http://localhost:3000/member/social/callback/kakao");
        body.add("code", "zF_CVgRkubvcdNWUuITuxSTAfZ69xojlq5X9Xfv4KLXSeCXQ2ioHZAAAAAQKFwYuAAABmBuFXNHE017PSiBv1Q");
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body,headers);

        String requestUrl = "https://kauth.kakao.com/oauth/token";

        ResponseEntity<String> response = restTemplate.exchange(URI.create(requestUrl), HttpMethod.POST, request, String.class);
        HttpStatusCode status = response.getStatusCode();
        System.out.println("status: " + status);
        System.out.println(response.getBody());
    }
}
