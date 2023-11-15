package com.example.whisperworld.mapper;

import com.example.whisperworld.entity.Friends;
import com.example.whisperworld.entity.PrivateMessage;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface companionMapper {
    @Select("SELECT userName FROM users WHERE userID in (SELECT friendId FROM Friends WHERE userId=#{userId} )")
    List<String> getAllFriends(Integer userId);
    @Insert("INSERT INTO friends VALUES(#{userId},#{friendId},false)")
    int applyFriend(Friends friends);
    @Select("SELECT userName FROM users WHERE userID IN (SELECT friendID FROM Friends WHERE userID = #{userId} ) AND userName LIKE CONCAT(#{prefix}, '%')")
    List<String> getFriendsByNAME(Integer userId, String prefix);
    @Select("SELECT userName FROM users WHERE userID NOT IN (SELECT userID FROM Friends WHERE friendID = #{userId} ) AND userName LIKE CONCAT(#{prefix}, '%') AND userID NOT IN (#{userId})")
    List<String> getPeopleByNAME(Integer userId, String prefix);
    @Update("UPDATE private_messages SET ReceiveState=true WHERE userID=#{userId} AND ReceiverID=#{receiverId}")
    void setReceived(Integer userId,Integer receiverId);
    @Select("SELECT MessageContent,SendTime,ReceiveState FROM private_messages WHERE UserID=#{userId} and ReceiverID=#{receiverId} ORDER BY MessageContentID ASC")
    List<PrivateMessage> getMessagesFromA2B(Integer userId,Integer receiverId);
    @Insert("INSERT INTO private_messages VALUES(#{userId},#{receiverId},#{messageContent},#{messageContentId},#{sendTime},#{receiveState})")
    int insertMessage(PrivateMessage msg);
    @Select("SELECT userID FROM users WHERE userName=#{name}")
    Integer getIDByName(String name);
    @Select("SELECT COUNT(*) FROM private_messages WHERE UserID=#{userId} and ReceiverID=#{receiverId}")
    int getMsgCount(PrivateMessage msg);
}
