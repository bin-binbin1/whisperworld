package com.example.whisperworld.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface login_mapper {
    @Select("SELECT userPassword from users where userName=#{username}")
    String login_pwd(String username);//匹配用户密码
}
