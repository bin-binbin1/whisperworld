package com.example.whisperworld.service;

import com.example.whisperworld.entity.Notification;
import com.example.whisperworld.mapper.homeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class homeService {
    private homeMapper mapper;

    @Autowired
    public homeService(homeMapper mapper){
        this.mapper=mapper;
    }
    public List<Notification> getAllnotices(){
        return mapper.getAllNotice();
    }

    public List<String> getAllRequestFriendName(Integer userId){
        return mapper.getFriendNames(userId);
    }

}
