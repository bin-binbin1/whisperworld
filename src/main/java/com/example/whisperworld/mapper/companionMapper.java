package com.example.whisperworld.mapper;

import com.example.whisperworld.entity.Friends;
import com.example.whisperworld.entity.PrivateMessage;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface companionMapper {
    @Select("SELECT userName FROM users WHERE userID in (SELECT friendId FROM friends WHERE userId=#{userId} AND STATE=true )")
    List<String> getAllFriends(Integer userId);
    @Insert("INSERT INTO friends VALUES(#{userId},#{friendId},false,false)")
    int applyFriend(Friends friends);
    @Delete("DELETE FROM friends WHERE userID=#{userID} AND FriendID=#{friendID}")
    void deleteFriends(Integer userID,Integer friendID);
    @Delete("DELETE FROM private_messages WHERE userID=#{userID} AND ReceiverID=#{friendID}")
    void deleteMsgs(Integer userID,Integer friendID);
    @Select("SELECT userName FROM users WHERE userID IN (SELECT friendID FROM friends WHERE userID = #{userId} AND STATE=true ) AND userName LIKE CONCAT(#{prefix}, '%')")
    List<String> getFriendsByNAME(Integer userId, String prefix);
    @Select("SELECT userName FROM users WHERE userID NOT IN (SELECT userID FROM friends WHERE friendID = #{userId} ) AND userName LIKE CONCAT(#{prefix}, '%') AND userID NOT IN (#{userId})")
    List<String> getPeopleByNAME(Integer userId, String prefix);
    @Update("UPDATE private_messages SET ReceiveState=true WHERE userID=#{userId} AND ReceiverID=#{receiverId}")
    void setReceived(Integer userId,Integer receiverId);
    @Select("SELECT MessageContent,SendTime,ReceiveState,UserID FROM private_messages WHERE UserID=#{receiverID} and ReceiverID=#{userID} and ReceiveState=false")
    List<PrivateMessage> getUnreadMsgs(Integer userID,Integer receiverID);
    @Insert("INSERT INTO private_messages VALUES(#{userId},#{receiverId},#{messageContent},#{messageContentId},#{sendTime},#{receiveState})")
    int insertMessage(PrivateMessage msg);
    @Select("SELECT userID FROM users WHERE userName=#{name}")
    Integer getIDByName(String name);
    @Select("SELECT COUNT(*) FROM private_messages WHERE UserID=#{userId} and ReceiverID=#{receiverId}")
    int getMsgCount(PrivateMessage msg);
    @Select("SELECT userName FROM users WHERE userID=#{userID}")
    String getNameByID(Integer userID);
    @Select("(SELECT MessageContent,SendTime,ReceiveState,UserID FROM private_messages WHERE UserID=#{userID} AND ReceiverID=#{friendID})" +
            "UNION ALL" +
            "(SELECT MessageContent,SendTime,ReceiveState,UserID FROM private_messages WHERE UserID=#{friendID} AND ReceiverID=#{userID})" +
            "ORDER BY SendTime DESC LIMIT #{start_length},#{msg_length}")
    List<PrivateMessage> getMoreHistory(Integer userID,Integer friendID,int start_length,int msg_length);
}
