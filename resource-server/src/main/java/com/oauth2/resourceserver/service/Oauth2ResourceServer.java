package com.oauth2.resourceserver.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.IOUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.io.IOException;

@Log4j2
@Configuration
@RequiredArgsConstructor
@EnableResourceServer
public class Oauth2ResourceServer extends ResourceServerConfigurerAdapter {

    private final OAuth2ProtectedResourceDetails resourceDetails;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable();
        http.authorizeRequests()
//                .antMatchers("/v1/users")
                .antMatchers("/resource/total-cmd/**").permitAll()
                .antMatchers("/resource/user-info")
                .access("#oauth2.isOAuth()")
                .antMatchers("/resource/gw-upload-file")
                .access("#oauth2.isOAuth()")
                .anyRequest()
                .authenticated();
    }

    @Bean
    public TokenStore tokenStore(){
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        Resource resource = new ClassPathResource("publicKey.txt");
        String publicKey = null;
        try {
            publicKey = IOUtils.toString(resource.getInputStream());
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
        converter.setVerifierKey(publicKey);
        return converter;
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        String resourceId = resourceDetails.getId();
        System.out.println("resource id : "+resourceId);
        resources.resourceId(resourceId);
    }
}
