package com.oauth2.clientserver.controller;

import com.google.gson.Gson;
import com.oauth2.clientserver.config.CommonConfig;
import com.oauth2.clientserver.config.Oauth2Config;
import com.oauth2.clientserver.vo.OAuthToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/client")
public class ClientController {
    private final Gson gson;
    private final Oauth2Config oauth2Config;
    private final CommonConfig commonConfig;
    private final RestTemplate restTemplate;

    @PostMapping("/total-cmd")
    public void totalCmd(){
        String credentials = oauth2Config.getClientId() + ":" + oauth2Config.getClientSecret();
        String encodedCredentials = new String(Base64.encodeBase64(credentials.getBytes()));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("Authorization", "Basic " + encodedCredentials);

        // 인증 서버로부터 Access Token 을 발급 받기 위해 필요한 파라미터 생성
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", oauth2Config.getGrantType());
        params.add("scope",oauth2Config.getScope());
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        // Access Token 발급 요청
        ResponseEntity<String> response = restTemplate.postForEntity(oauth2Config.getAuthenticationServerUrl(), request, String.class);
        OAuthToken oAuthToken = gson.fromJson(response.getBody(), OAuthToken.class);

        if (oAuthToken != null) {
            // TODO: 2020-11-30 Access Token 을 얻었다면 리소스 서버로 데이터 요청하는 로직 작성
            uploadData(oAuthToken);
        }
    }

    public void uploadData(OAuthToken oAuthToken){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        String url = commonConfig.getGwUrl();
        log.info("##### upload Data to url : {}", url);
        /**
         * Token 담는 로직
         **/
        headers.add("Authorization","Bearer "+oAuthToken.getAccessToken());

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("accessToken", oAuthToken.getAccessToken());
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
        if (!response.getStatusCode().is2xxSuccessful())
            log.error("##### Failed Request to Gw Dummy Application. status code : {}, url : {}", response.getStatusCode(), url);
    }
}
