package com.study.egovspringbootmybatis.config;

import com.study.egovspringbootmybatis.service.oauth2.CustomOAuth2AuthorizedClientService;
import com.study.egovspringbootmybatis.service.oauth2.CustomOAuth2UserService;
import com.study.egovspringbootmybatis.vo.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.logout.HeaderWriterLogoutHandler;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;

import static org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter.Directive.*;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity // SpringSecurityFilterChain 자동 구현
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomOAuth2AuthorizedClientService customAuthorizedClientService;

    private static final ClearSiteDataHeaderWriter.Directive[] SOURCE =
            {CACHE, COOKIES, STORAGE, EXECUTION_CONTEXTS};

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()// 설정을 명시적으로 보여주는 것이다.
                .headers().frameOptions().disable()
                .and()
                .authorizeRequests()
                .antMatchers("/", "/login", "/css/**", "/images/**",
                        "/js/**")
                .permitAll()
                .antMatchers("/api/v1/**")
                .hasRole(Role.USER.name())
                .anyRequest().authenticated()
                .and()
                .logout(logout -> logout
                                .logoutUrl("/logout")
                                .addLogoutHandler(new HeaderWriterLogoutHandler(new ClearSiteDataHeaderWriter(SOURCE)))
                )
                .oauth2Login()
                .authorizationEndpoint()
                .and()
                .authorizedClientService(customAuthorizedClientService)
                .userInfoEndpoint()
                .userService(customOAuth2UserService);
    }
}
