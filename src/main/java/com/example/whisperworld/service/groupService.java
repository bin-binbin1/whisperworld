package com.example.whisperworld.service;

import com.example.whisperworld.entity.CrowdsMessage;
import com.example.whisperworld.mapper.groupChatMapper;
import com.example.whisperworld.mapper.groupMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<String>members(Integer groupId){//查询组内所有成员
        return groupChatMapper.members(groupId);
    }

    public Boolean message(CrowdsMessage crowdsMessage){//发送消息
        return groupChatMapper.sendMessage(crowdsMessage);
    }

    public List<String> userMessage(Integer userID,Integer groupID){//查询用户自己发送的消息
        return groupChatMapper.userMessage(groupID,userID);
    }
}
