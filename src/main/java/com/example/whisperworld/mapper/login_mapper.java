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

    @Select("SELECT userID from users where userName=#{username} ")git 
    Integer login_id(String username);//查找用户id

    @Select("SELECT supervisorId from supervisors where supervisorId=#{userID}")
    Integer isSuper(String userID);//判断是否为管理员

    @Select("SELECT * FROM supervisors WHERE supervisorId=#{userID}")
    Supervisor loginSuper(String userID);//获取管理员信息
}
