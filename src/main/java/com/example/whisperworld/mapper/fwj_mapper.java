package com.example.whisperworld.mapper;


import com.example.whisperworld.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface fwj_mapper {

    @Select("select * from users")
    List<User> findAll();
}
