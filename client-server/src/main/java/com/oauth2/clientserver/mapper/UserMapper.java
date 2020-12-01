package com.oauth2.clientserver.mapper;

import com.oauth2.clientserver.vo.UserVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;


@Mapper
@Repository
public interface UserMapper {
    void insertData(UserVo o) throws Exception;
    void updateData(UserVo o) throws Exception;
    void deleteData(UserVo o) throws Exception;
    UserVo selectDataById(String id) throws Exception;
    List<UserVo> searchByAll();
}
