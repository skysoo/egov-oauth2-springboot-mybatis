package com.study.egovspringbootmybatis.service;

import com.study.egovspringbootmybatis.dao.UserInfoDao;
import com.study.egovspringbootmybatis.mapper.UserInfoMapper;
import com.study.egovspringbootmybatis.vo.UserInfoVO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserInfoDaoImpl extends EgovAbstractServiceImpl implements UserInfoDao {
    private final UserInfoMapper userInfoMapper;

    public UserInfoDaoImpl(UserInfoMapper userInfoMapper) {
        this.userInfoMapper = userInfoMapper;
    }

    @Override
    public void insertData(UserInfoVO o) throws Exception {
        userInfoMapper.insertData(o);
    }

    @Override
    public void updateData(UserInfoVO o) throws Exception {

    }

    @Override
    public void deleteData(UserInfoVO o) throws Exception {

    }

    @Override
    public UserInfoVO selectDataById(String id) throws Exception {
        return userInfoMapper.selectDataById(id);
    }

    @Override
    public List<UserInfoVO> searchByAll() {
        return userInfoMapper.searchByAll();
    }
}
