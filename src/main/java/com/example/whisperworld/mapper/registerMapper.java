package com.example.whisperworld.mapper;


import com.example.whisperworld.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface registerMapper {

    @Select("SELECT * FROM users")
    List<User> findAll();

    @Select("SELECT count(*) FROM users")
    int getUserNum();

    @Insert("INSERT INTO users VALUES(#{userID}, #{userName}, #{userPassword}, #{userSex}, #{urDate}, #{userPhone}, #{userState}, #{userLevel}, #{userBDate}) ")
    int insertUser(User user);

    @Select("SELECT count(*) FROM users WHERE userName=#{userName}")
    int getSameNameCount(User user);

}
