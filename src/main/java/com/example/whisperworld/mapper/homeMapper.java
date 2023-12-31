package com.example.whisperworld.mapper;

import com.example.whisperworld.entity.Friends;
import com.example.whisperworld.entity.Notification;
import com.example.whisperworld.specialClasses.groupRequest;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface homeMapper {

    @Select("SELECT * FROM notifications ORDER BY notificationId DESC")
    List<Notification> getAllNotice();
    @Select("SELECT userName FROM users WHERE userID in (SELECT FriendID FROM friends WHERE userID=#{userId} AND STATE=false)")
    List<String> getFriendNames(Integer userId);
    @Update("UPDATE friends set STATE=true WHERE UserID=#{userId} AND friendID=#{friendId}")
    int setFriendPass(Integer userId,Integer friendId);

    @Insert("INSERT INTO friends VALUES (#{friendId}, #{userId}, true, false)")
    int insertFriend(Integer userId,Integer friendId);

    @Select("SELECT userID FROM users WHERE userName=#{name}")
    Integer getIDByName(String name);

    @Delete("DELETE FROM friends WHERE userID=#{userId} AND friendID=#{friendId}")
    int deleteFriendApply(Integer userId,Integer friendId);
    @Select("SELECT c.GroupName,c.GroupID,u.userName " +
            "FROM crowds c " +
            "INNER JOIN crowds_members m ON c.GroupID = m.GroupID " +
            "INNER JOIN users u ON m.MemberID = u.userID WHERE c.GroupID IN " +
            "(SELECT GroupID from crowds WHERE MasterID=#{userId}) AND m.STATE=FALSE")
    List<groupRequest> getAllGroupRequest(Integer userId);
    @Update("UPDATE crowds_members set STATE=true WHERE GroupID=#{groupId} AND MemberID=#{userId}")
    int setGroupMemberPass(Integer groupId,Integer userId);
    @Delete("DELETE FROM crowds_members WHERE GroupID=#{groupId} AND MemberID=#{userId}")
    int setGroupMemberUnpass(Integer groupId,Integer userId);
}
