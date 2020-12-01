package com.oauth2.resourceserver.dao;


import com.oauth2.resourceserver.mapper.UserMapper;
import com.oauth2.resourceserver.vo.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserImpl implements UserDao {
    private final UserMapper userMapper;

    @Override
    public void insertData(UserVO o) throws Exception {

    }

    @Override
    public void updateData(UserVO o) throws Exception {

    }

    @Override
    public void deleteData(UserVO o) throws Exception {

    }

    @Override
    public UserVO selectDataById(String id) throws Exception {
        return userMapper.selectDataById(id);
    }

    @Override
    public List<UserVO> searchByAll() {
        return null;
    }
}
