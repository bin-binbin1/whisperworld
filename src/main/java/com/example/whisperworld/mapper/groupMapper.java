package com.example.whisperworld.mapper;

import com.example.whisperworld.entity.Crowds;
import com.example.whisperworld.specialClasses.groups;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface groupMapper {
    @Select(" SELECT GroupName AS groupName,crowds.GroupID AS groupId,MasterID AS masterId FROM crowds JOIN crowds_members ON crowds.GroupID=crowds_members.GroupID WHERE MemberID=#{userID};")
    List<Map<String,Object>> groups(Integer userID);//查询用户所属组
    @Select("SELECT GroupName AS groupName, GroupID AS groupId,MasterID AS masterId FROM crowds WHERE GroupName LIKE CONCAT('%',#{input},'%')")
    List<Map<String,Object>> searchGroups(String input);//检索群聊
    @Insert("INSERT INTO crowds VALUES (#{groupId},#{masterId},#{managerId},#{groupName},#{createDate},#{groupNum},#{groupBackground})")
    Boolean createGroup(Crowds crowd);//创建群组
    @Insert("INSERT INTO crowds_members VALUES (#{GroupID},#{MemberID})")
    Boolean insertMember(Integer GroupID,Integer MemberID);//添加成员
    @Select("SELECT MAX(GroupID) FROM crowds ")
    Integer countCrowds();//查找话题数目



}
