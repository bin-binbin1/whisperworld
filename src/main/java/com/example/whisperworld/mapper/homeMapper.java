package com.example.whisperworld.mapper;

import com.example.whisperworld.entity.Friends;
import com.example.whisperworld.entity.Notification;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface homeMapper {

    @Select("SELECT * FROM notifications ORDER BY notificationId DESC")
    List<Notification> getAllNotice();
    @Select("SELECT userName FROM users WHERE userID in (SELECT FriendID FROM friends WHERE userID=#{userId} AND STATE=false)")
    List<String> getFriendNames(Integer userId);
    @Update("UPDATE friends set STATE=true WHERE UserID=#{userId} AND friendID=#{friendId}")
    int setFriendPass(Friends friends);

    @Insert("INSERT INTO friends VALUES (#{friendId}, #{userId}, true)")
    int insertFriend(Friends friends);

    @Select("SELECT userID FROM users WHERE userName=#{name}")
    Integer getIDByName(String name);

    @Delete("DELETE FROM friends WHERE userID=#{userId} AND friendID=#{friendId}")
    int deleteFriendApply(Friends friends);

}
