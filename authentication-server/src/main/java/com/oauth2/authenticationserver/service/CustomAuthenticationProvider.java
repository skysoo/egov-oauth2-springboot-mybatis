package com.oauth2.authenticationserver.service;

import com.oauth2.authenticationserver.dao.UserDao;
import com.oauth2.authenticationserver.vo.UserVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private final PasswordEncoder passwordEncoder;
    private final UserDao userDao;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String name = authentication.getName();
        String password = authentication.getCredentials().toString();

        UserVo userVo = null;
        try {
            userVo = userDao.selectDataById(name);
        } catch (Exception e) {
            log.error(e);
        }

        if (!passwordEncoder.matches(password, userVo.getPassword()))
            throw new BadCredentialsException("password is not valid");

        return new UsernamePasswordAuthenticationToken(name, password, userVo.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
