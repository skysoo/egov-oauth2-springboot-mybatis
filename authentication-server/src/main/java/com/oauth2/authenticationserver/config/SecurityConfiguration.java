package com.oauth2.authenticationserver.config;

import com.oauth2.authenticationserver.service.CustomAuthenticationProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity // SpringSecurityFilterChain 자동 구현
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final CustomAuthenticationProvider customAuthenticationProvider;

    public SecurityConfiguration(CustomAuthenticationProvider customAuthenticationProvider) {
        this.customAuthenticationProvider = customAuthenticationProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(customAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .authorizeRequests().antMatchers("/oauth/**",
                "/oauth/token",
                "/oauth2/callback").permitAll()
                .and()
                .formLogin().and()
                .httpBasic();
    }
}
