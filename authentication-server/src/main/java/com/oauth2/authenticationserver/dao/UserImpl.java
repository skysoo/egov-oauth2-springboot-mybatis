package com.oauth2.authenticationserver.dao;

import com.oauth2.authenticationserver.mapper.UserMapper;
import com.oauth2.authenticationserver.vo.UserVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class UserImpl implements UserDao {
    private final UserMapper userMapper;

    @Override
    public void insertData(UserVo o) throws Exception {

    }

    @Override
    public void updateData(UserVo o) throws Exception {

    }

    @Override
    public void deleteData(UserVo o) throws Exception {

    }

    @Override
    public UserVo selectDataById(String id) throws Exception {
        return userMapper.selectDataById(id);
    }

    @Override
    public List<UserVo> searchByAll() {
        return null;
    }
}
