package com.oauth2.authenticationserver.service;

import com.oauth2.authenticationserver.dao.UserDao;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private UserDao userDao;

    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userDao.selectDataById(s);
    }
}
