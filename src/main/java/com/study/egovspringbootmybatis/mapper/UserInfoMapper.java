package com.study.egovspringbootmybatis.mapper;


import com.study.egovspringbootmybatis.vo.UserInfoVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author skysoo
 * @version 1.0.0
 * @since 2020-06-12 오후 5:51
 **/
@Mapper
@Repository
public interface UserInfoMapper {
    void insertData(UserInfoVO o) throws Exception;
    void updateData(UserInfoVO o) throws Exception;
    void deleteData(UserInfoVO o) throws Exception;
    UserInfoVO selectDataById(String id) throws Exception;
    List<UserInfoVO> searchByAll();
}
