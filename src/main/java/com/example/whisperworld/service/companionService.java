package com.example.whisperworld.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.whisperworld.mapper.companionMapper;

import java.util.List;

@Service
public class companionService {
    private companionMapper mapper;
    @Autowired
    public companionService(companionMapper mapper){
        this.mapper=mapper;
    }

    public List<String> getAllFriends(Integer userId){
        return mapper.getAllFriends(userId);
    }
    public List<String> getFriendsByName(String prefix){
        return mapper.getFriendsByNAME(prefix);
    }

    public


}
