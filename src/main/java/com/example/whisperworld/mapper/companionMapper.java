package com.example.whisperworld.mapper;

import com.example.whisperworld.entity.PrivateMessage;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface companionMapper {
    @Select("SELECT userName FROM users WHERE userID in (SELECT friendId FROM Friends WHERE userId=#{userId} )")
    List<String> getAllFriends(Integer userId);

    @Select("SELECT userName FROM users WHERE userID IN (SELECT friendId FROM Friends WHERE userId = #{userId} ) AND userName LIKE CONCAT(#{prefix}, '%')")
    List<String> getFriendsByNAME(Integer userId, String prefix);

    @Select("SELECT MessageContent,SendTime,ReceiveState FROM private_messages WHERE UserID=#{userId} and ReceiverID=#{receiverId} ORDER BY MessageContentID DESC")
    List<PrivateMessage> getMessagesFromA2B(Integer userId,Integer receiverId);

    @Insert("INSERT INTO private_messages VALUES(#{UserID},#{ReceiverID},#{MessageContent},#{MessageContentID},#{SendTime},#{ReceiverState})")
    int insertMessage(PrivateMessage msg);

    @Select("SELECT userID FROM users WHERE userName=#{name}")
    Integer getIDByName(String name);

    @Select("SELECT COUNT(*) FROM private_messages WHERE UserID=#{userId} and ReceiverID=#{receiverId}")
    int getMsgCount(PrivateMessage msg);
}
