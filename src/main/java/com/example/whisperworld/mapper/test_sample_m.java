package com.example.whisperworld.mapper;

// UserRepository.java
import java.util.List;
import com.example.whisperworld.entity.User;//要从数据库内拿东西，就从entity拿表
import org.apache.ibatis.annotations.Select;

public interface test_sample_m {

    @Select("select * from users where userID = id")
    User findById(Long id);

}
