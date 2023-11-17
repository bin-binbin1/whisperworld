package com.example.whisperworld.service;

import com.example.whisperworld.mapper.groupMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class groupService {
    private final groupMapper groupmapper;
    @Autowired
    public groupService(groupMapper groupmapper){
        this.groupmapper = groupmapper;
    }


}
