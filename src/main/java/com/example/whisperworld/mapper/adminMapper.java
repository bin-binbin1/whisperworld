package com.example.whisperworld.mapper;


import com.example.whisperworld.specialClasses.oneTopic;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface adminMapper {
    @Select("SELECT userName,TopicLaunchTime,TopicContent FROM topics JOIN users ON topics.userID = users.userID")
    List<oneTopic> getTopics();
    @Select("SELECT COUNT(*) from supervisors WHERE SupervisorID=#{userID}")
    int getSupervisorCountByID(Integer userID);
    @Delete("DELETE FROM users WHERE topicID=#{topicID} ")
    void deleteTopicByID(Integer topicID);
    @Select("SELECT COUNT(*) FROM notifications")
    int getNoticeCount();
}
