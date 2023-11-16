package com.example.whisperworld.mapper;


import com.example.whisperworld.entity.Notification;
import com.example.whisperworld.specialClasses.oneTopic;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface adminMapper {
    @Select("SELECT userName,TopicID,TopicLaunchTime,TopicContent FROM topics JOIN users ON topics.userID = users.userID")
    List<oneTopic> getTopics();
    @Select("SELECT COUNT(*) from supervisors WHERE SupervisorID=#{userID}")
    int getSupervisorCountByID(Integer userID);
    @Delete("DELETE FROM likes WHERE TopicID=#{topicID}")
    void deleteLikes(Integer topicID);
    @Delete("DELETE FROM topic_replies WHERE TopicID=#{topicID}")
    void deleteRepliesByID(Integer topicID);
    @Delete("DELETE FROM topics WHERE TopicID=#{topicID} ")
    void deleteTopicByID(Integer topicID);
    @Select("SELECT COUNT(*) FROM notifications")
    int getNoticeCount();
    @Insert("INSERT INTO notifications values (#{notificationId},#{notificationContent},#{notificationTime},#{supervisorId})")
    int insertNotice(Notification notification);
}
