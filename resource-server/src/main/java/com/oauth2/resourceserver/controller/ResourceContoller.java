package com.oauth2.resourceserver.controller;

import com.oauth2.resourceserver.config.CommonConfig;
import com.oauth2.resourceserver.vo.UserVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping("/resource")
public class ResourceContoller{
    private final CommonConfig commonConfig;
    private final RestTemplate restTemplate;

    @PostMapping("/total-cmd")
    public void totalCmd() throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        String url = commonConfig.getClientUrl();

        HttpPost httpPost = new HttpPost(url);
        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
        httpPost.setEntity(multipartEntityBuilder.build());

        if (httpPost.getEntity()!=null){
            int statusCode = httpClient.execute(httpPost).getStatusLine().getStatusCode();
            if (!String.valueOf(statusCode).startsWith("2"))
                log.error("##### Failed Request to Gw Dummy Application. status code : {}, url : {}", statusCode, url);
        }
    }

    @GetMapping("user-info")
    public UserVO getUserInfo(@RequestParam String userId){
        return null;
    }

    @PostMapping("/gw-upload-data")
    public void saveZipFile(@RequestParam String accessToken){
        log.info("##### accessToken : "+accessToken);
    }
}
