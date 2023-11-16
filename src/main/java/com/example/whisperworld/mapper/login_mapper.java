package com.example.whisperworld.mapper;

import com.example.whisperworld.entity.Supervisor;
import com.example.whisperworld.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface login_mapper {
    @Select("SELECT userPassword,userID from users where userID=#{userID}")
    User login_pwd(Integer userID);//匹配用户密码

    @Select("SELECT userID from users where userName=#{username} ")
    Integer login_id(String username);//查找用户id

    @Select("SELECT * FROM supervisors WHERE supervisorId=#{userID}")
    Supervisor loginSuper(Integer userID);//获取管理员信息
}
