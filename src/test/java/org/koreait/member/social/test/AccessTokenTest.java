package org.koreait.member.social.test;

import org.junit.jupiter.api.Test;
import org.koreait.global.libs.Utils;
import org.koreait.member.social.entities.AuthToken;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Map;

@SpringBootTest
public class AccessTokenTest {

    private RestTemplate restTemplate;
    private Utils utils;

    @Test
    void test() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", "554171db1e052c89471da8f35fb9e6ac");
        body.add("redirect_uri", "http://localhost:3000/member/social/callback/kakao");
        body.add("code", "GhN26ZZc0vZ_ZRbWZM-e82TefniuqNjVYV9z0vEfelrj3dwI7ZzFdwAAAAQKFwtrAAABmCcCebui-KZYUq23DA");
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body,headers);

        String requestUrl = "https://kauth.kakao.com/oauth/token";

        ResponseEntity<AuthToken> response = restTemplate.exchange(URI.create(requestUrl), HttpMethod.POST, request, AuthToken.class);
        HttpStatusCode status = response.getStatusCode();
        System.out.println("status: " + status);
        System.out.println(response.getBody());

        AuthToken authToken = response.getBody();
        requestUrl = "https://kapi.kakao.com/v2/user/me";
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBearerAuth(authToken.getAccessToken());

        request = new HttpEntity<>(headers);
        ResponseEntity<Map> res = restTemplate.exchange(URI.create(requestUrl), HttpMethod.POST, request, Map.class);

        Map resBody = res.getBody();
        long id = (Long)resBody.get("id");
        System.out.println(id);
    }
}
