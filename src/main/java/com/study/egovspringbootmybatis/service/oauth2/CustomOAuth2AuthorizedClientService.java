package com.study.egovspringbootmybatis.service.oauth2;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class CustomOAuth2AuthorizedClientService implements OAuth2AuthorizedClientService {
    @Override
    public <T extends OAuth2AuthorizedClient> T loadAuthorizedClient(String s, String s1) {
        System.out.println("Aa");
        return null;
    }

    @Override
    public void saveAuthorizedClient(OAuth2AuthorizedClient oAuth2AuthorizedClient, Authentication authentication) {
        OAuth2AccessToken accessToken = oAuth2AuthorizedClient.getAccessToken();
        log.info("@@ Access TOken = {}", accessToken.getTokenType());
        log.info("@@ Access TOken = {}", accessToken.getExpiresAt());
        log.info("@@ Access TOken = {}", accessToken.getScopes());
        log.info("@@ Access TOken = {}", accessToken.getTokenValue());
    }

    @Override
    public void removeAuthorizedClient(String s, String s1) {
        System.out.println("aa");
    }
}