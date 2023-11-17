package com.example.whisperworld.service;

import com.example.whisperworld.entity.CrowdsMessage;
import com.example.whisperworld.mapper.groupChatMapper;
import com.example.whisperworld.mapper.groupMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class groupService {
    private final groupChatMapper groupChatMapper;
    @Autowired
    public groupService(groupChatMapper groupChatMapper){
        this.groupChatMapper = groupChatMapper;
    }

    public List<String>crowds(Integer userID){//查询用户所有群组
        return groupChatMapper.groups(userID);
    }

    public List<String>members(String groupName){//查询组内所有成员
        return groupChatMapper.members(groupName);
    }

    public Boolean message(CrowdsMessage crowdsMessage){//发送消息
        return groupChatMapper.sendMessage(crowdsMessage);
    }

    public List<String> userMessage(Integer userID,Integer groupID){//查询用户自己发送的消息
        return groupChatMapper.userMessage(groupID,userID);
    }

    public String namesToJSON(List<String> names, String keys){
        List<Map<String,Object>> responses = new ArrayList<>();
        for(String name : names){
            Map<String,Object> response = new HashMap<>();
            response.put(keys,name);
            responses.add(response);
            //System.out.println("groupsname"+name);
        }
        ObjectMapper mapper = new ObjectMapper();
        String json="";
        try {
            json = mapper.writeValueAsString(responses);
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }
        return json;
    }
}
