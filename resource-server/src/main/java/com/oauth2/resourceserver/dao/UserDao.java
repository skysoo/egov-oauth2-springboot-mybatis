package com.oauth2.resourceserver.dao;

import com.oauth2.resourceserver.vo.UserVO;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface UserDao {
    void insertData(UserVO o) throws Exception;
    void updateData(UserVO o) throws Exception;
    void deleteData(UserVO o) throws Exception;
    UserVO selectDataById(String id) throws Exception, UsernameNotFoundException;
    List<UserVO> searchByAll();
}
