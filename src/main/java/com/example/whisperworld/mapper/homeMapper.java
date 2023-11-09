package com.example.whisperworld.mapper;

import com.example.whisperworld.entity.Notification;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface homeMapper {

    @Select("SELECT * FROM Notification")
    Notification getAllNotice();
}
