package com.example.whisperworld.mapper;

import com.example.whisperworld.entity.Notification;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface homeMapper {

    @Select("SELECT * FROM notifications ORDER BY notificationId DESC")
    List<Notification> getAllNotice();
    @Select("SELECT userName FROM users WHERE userID in (SELECT FriendID FROM friends WHERE userID=#{userId} AND STATE=false)")
    List<String> getFriendNames(Integer userId);
}
