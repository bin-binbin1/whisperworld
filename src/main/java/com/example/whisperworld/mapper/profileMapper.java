package com.example.whisperworld.mapper;

// UserRepository.java
import com.example.whisperworld.entity.User;//要从数据库内拿东西，就从entity拿表
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface profileMapper {

    @Select("select * from users where userID = #{id}")
    User getUserById(Integer id);

}
