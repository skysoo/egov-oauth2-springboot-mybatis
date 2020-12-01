package com.oauth2.authenticationserver.dao;

import com.oauth2.authenticationserver.vo.UserVo;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;


public interface UserDao {
    void insertData(UserVo o) throws Exception;
    void updateData(UserVo o) throws Exception;
    void deleteData(UserVo o) throws Exception;
    UserVo selectDataById(String id) throws Exception, UsernameNotFoundException;
    List<UserVo> searchByAll();
}
