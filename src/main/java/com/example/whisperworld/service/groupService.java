package com.example.whisperworld.service;

import com.example.whisperworld.entity.CrowdsMessage;
import com.example.whisperworld.mapper.groupChatMapper;
import com.example.whisperworld.mapper.groupMapper;
import com.example.whisperworld.specialClasses.groups;
import com.example.whisperworld.specialClasses.historyMsg;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class groupService {
    private final groupChatMapper groupChatMapper;
    @Autowired
    public groupService(groupChatMapper groupChatMapper){
        this.groupChatMapper = groupChatMapper;
    }

    public List<groups>crowds(Integer userID){//查询用户所有群组
        return groupChatMapper.groups(userID);
    }

    public List<String>members(Integer groupId){//查询组内所有成员
        return groupChatMapper.members(groupId);
    }

    public Boolean message(CrowdsMessage crowdsMessage){//发送消息
        crowdsMessage.setGroupMessageId(groupChatMapper.countGroupMsg(crowdsMessage.getGroupId()));
        return groupChatMapper.sendMessage(crowdsMessage);
    }
    public List<Integer> toOthers(Integer userID){
        return groupChatMapper.toOtherMembers(userID);
    }

    public List<String> userMessage(Integer userID,Integer groupID){//查询用户自己发送的消息
        return groupChatMapper.userMessage(groupID,userID);
    }

    public  List<historyMsg> historyMsgs(Integer userID,Integer groupID,Integer num){//查询群聊历史消息
        List<historyMsg> historyMsgs = groupChatMapper.historyMsg(userID,groupID,num);
        List<historyMsg> historyMsgs1 = new ArrayList<>();
        for(int i = historyMsgs.size()-1;i >= 0;i--)
        {
            historyMsgs1.add(historyMsgs.get(i));
        }
        return historyMsgs1;
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
