package com.oauth2.resourceserver.mapper;


import com.oauth2.resourceserver.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {
    void insertData(UserVO o) throws Exception;
    void updateData(UserVO o) throws Exception;
    void deleteData(UserVO o) throws Exception;
    UserVO selectDataById(String id) throws Exception;
    List<UserVO> searchByAll();
}
