package org.koreait.member.social.test;

import org.junit.jupiter.api.Test;
import org.koreait.member.social.entities.AuthToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Map;

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
        body.add("code", "QT090_D-Sl6cXOmwhIUmUY5Cx4ZpY61HB6lP8DE6zw_eCxYVnpw3YQAAAAQKDSCbAAABmButIFP7Ewsnpgvovw ");
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
