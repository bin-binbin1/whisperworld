package com.example.whisperworld.service;

import com.example.whisperworld.entity.Notification;
import com.example.whisperworld.mapper.adminMapper;
import com.example.whisperworld.specialClasses.oneTopic;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class adminService {
    private final adminMapper mapper;
    private int notificationCount;
    @Autowired
    public adminService(adminMapper mapper){
        this.mapper=mapper;
        notificationCount = mapper.getNoticeCount();
    }
    public String getllTopics(){
        List<oneTopic> topics =mapper.getTopics();
        ObjectMapper objectMapper = new ObjectMapper();
        String json="";
        try {
            json = objectMapper.writeValueAsString(topics);
        }catch (Exception e){
            e.printStackTrace();
        }
        return json;

    }
    public void deleteTopic(Integer topicID,Integer userID){
        if(!isAdmin(userID)){
            return;
        }
        mapper.deleteRepliesByID(topicID);
        mapper.deleteTopicByID(topicID);
    }
    public void postNote(Integer userID, Notification notification){
        if(!isAdmin(userID)){
            return;
        }
        notification.setNotificationTime(new Date());
        notification.setNotificationId(notificationCount++);
        notification.setSupervisorId(userID);
    }

    private boolean isAdmin(Integer userID){
        return mapper.getSupervisorCountByID(userID)>=1;
    }


}
