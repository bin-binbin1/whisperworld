package com.example.whisperworld.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface login_mapper {
    @Select("SELECT userName from users where userName=#{username}")
    String login_name(String username);//查找用户名

    @Select("SELECT userPassword from users where userName=#{username}")
    String login_pwd(String username);



}
