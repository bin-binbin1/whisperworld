package com.example.whisperworld.mapper;

import com.example.whisperworld.entity.Crowds;
import com.example.whisperworld.entity.CrowdsMessage;
import com.example.whisperworld.specialClasses.groups;
import com.example.whisperworld.specialClasses.historyMsg;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface groupChatMapper {

    @Select(" SELECT GroupName,crowds.GroupID FROM crowds JOIN crowds_members ON crowds.GroupID=crowds_members.GroupID WHERE MemberID=#{userID};")
    List<groups> groups(Integer userID);//查询用户所属组

    @Select("SELECT userName FROM users JOIN crowds_members ON crowds_members.MemberID=users.userID JOIN crowds ON crowds.GroupID=crowds_members.GroupID WHERE crowds.GroupID=#{groupID}")
    List<String> members(Integer groupID);//查找群组内的所有成员

    @Insert("INSERT INTO crowds_messages VALUES (#{groupId},#{groupMessageId},#{messageContent},#{userId},#{sendTime})")
    Boolean sendMessage(CrowdsMessage crowdsMessage);//发送信息

    @Select("SELECT userName FROM users WHERE userID=#{userID}")
    String getName(Integer userID);
    @Select("SELECT MemberID FROM crowds_members WHERE MemberID <> #{userID};")
    List<Integer> toOtherMembers(Integer userID);//将信息发送给其他群组成员

    @Select("SELECT ConversationContent AS content,userName,SendTime AS time,CASE WHEN users.userID=#{userID} THEN 'true' ELSE 'false' END AS self FROM crowds_messages JOIN users ON crowds_messages.UserID=users.userID WHERE GroupID=#{groupID} ORDER BY SendTime DESC LIMIT 2 OFFSET #{num}")
    List<historyMsg> historyMsg(Integer userID,Integer groupID,Integer num);//查询群聊历史消息

    @Select("SELECT COUNT(*) FROM crowds_messages WHERE GroupID=#{groupID}")
    Integer countGroupMsg(Integer groupID);//群聊消息计数


}
