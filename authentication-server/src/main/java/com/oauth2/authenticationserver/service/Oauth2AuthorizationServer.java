package com.oauth2.authenticationserver.service;

import com.oauth2.authenticationserver.config.CommonConfiguration;
import com.oauth2.authenticationserver.vo.TokenTypeEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import javax.sql.DataSource;
import java.security.KeyPair;

@Configuration
@RequiredArgsConstructor
@EnableAuthorizationServer
public class Oauth2AuthorizationServer extends AuthorizationServerConfigurerAdapter {

    private final DataSource dataSource;
    private final PasswordEncoder passwordEncoder;
    private final CustomUserDetailService userDetailsService;
    private final CommonConfiguration commonConfiguration;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        /**
         * DB 이용 방식 - 관련 테이블이 미리 생성되어 있어야 한다.
         **/
        clients.jdbc(dataSource).passwordEncoder(passwordEncoder);
    }


    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        /**
         * Token 정보를 DB에 저장하는 로직
         * 로그인 시점에 토큰과 관련 정보들이 잡히게 된다.
         *
         * oauth_access_token 과 같은 테이블이 미리 생성되어 있어야 한다.
         **/
        switch (TokenTypeEnum.valueOf(commonConfiguration.getTokenType())){
            case BEARER:
                endpoints.tokenStore(new JdbcTokenStore(dataSource))
                         .userDetailsService(userDetailsService);
                break;
            case JWT:
                super.configure(endpoints);
                endpoints.accessTokenConverter(jwtAccessTokenConverter())
                        .userDetailsService(userDetailsService);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + TokenTypeEnum.valueOf(commonConfiguration.getTokenType()));
        }
    }

    // Resource 서버에서 보낸  Token 검증 요청을 처리하는 로직
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        System.out.println("##### token check");
        security.tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()") //allow check token
                .allowFormAuthenticationForClients();
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        KeyPair keyPair = new KeyStoreKeyFactory(new ClassPathResource("oauth2jwt.jks"), "oauth2jwtpass".toCharArray())
                .getKeyPair("oauth2jwt","oauth2jwtpass".toCharArray());
        converter.setKeyPair(keyPair);
        return converter;
    }
}

