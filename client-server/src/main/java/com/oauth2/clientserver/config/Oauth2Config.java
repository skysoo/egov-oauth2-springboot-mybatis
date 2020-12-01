package com.oauth2.clientserver.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "oauth")
public class Oauth2Config {
    private String clientId;
    private String clientSecret;
    private String scope;
    private String grantType;
    private String authenticationServerUrl;
}
