package com.example.whisperworld.mapper;

import com.example.whisperworld.entity.Crowds;
import com.example.whisperworld.entity.CrowdsMessage;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface groupChatMapper {

    @Select(" SELECT GroupName FROM crowds JOIN crowds_members ON crowds.GroupID=crowds_members.GroupID WHERE MemberID=#{userID};")
    List<String> groups(Integer userID);//查询用户所属组

    @Select("SELECT userName FROM users JOIN crowds_members ON crowds_members.MemberID=users.userID JOIN crowds ON crowds.GroupID=crowds_members.GroupID WHERE crowds.GroupID=#{groupID}")
    List<String> members(Integer groupID);//查找群组内的所有成员

    @Insert("INSERT INTO crowds_messages VALUES (#{groupID},#{groupMessageID},#{content},#{userID},#{sendTime})")
    Boolean sendMessage(CrowdsMessage crowdsMessage);//发送信息

    @Select("SELECT ConversationContent FROM cowds_messages WHERE GroupID=#{groupID} AND UserID=#{userID}")
    List<String> userMessage(Integer groupId, Integer userID);//查询用户自己的消息
}
