package com.example.whisperworld.service;

import com.example.whisperworld.entity.Notification;
import com.example.whisperworld.mapper.homeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class homeService {
    private homeMapper mapper;

    @Autowired
    public homeService(homeMapper mapper){
        this.mapper=mapper;
    }
    public Notification getAllnotices(){
        return mapper.getAllNotice();
    }

}
