package com.example.whisperworld.service;

import com.example.whisperworld.entity.PrivateMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.whisperworld.mapper.companionMapper;

import javax.servlet.http.HttpSession;
import java.util.*;

@Service
public class companionService {
    private final companionMapper mapper;
    @Autowired
    public companionService(companionMapper mapper){
        this.mapper=mapper;
    }

    public List<String> getAllFriends(Integer userId){
        return mapper.getAllFriends(userId);
    }
    public List<String> getFriendsByName(Integer userId,String prefix){
        return mapper.getFriendsByNAME(prefix);
    }
    public boolean sendMessage(String content,Integer userId,Integer friendId){
        PrivateMessage msg = new PrivateMessage();
        msg.setMessageContent(content);
        msg.setSendTime(new Date());
        msg.setUserId(userId);
        msg.setReceiverId(friendId);//后期优化
        msg.setMessageContentId(mapper.getMsgCount(msg));
        return mapper.insertMessage(msg)!=0;
    }
    public String getMessages(Integer userId, Integer receiverId){

        List<PrivateMessage> getMsg=mapper.getMessagesFromA2B(receiverId,userId);
        List<PrivateMessage> sendMsg=mapper.getMessagesFromA2B(userId,receiverId);
        for(PrivateMessage msg:getMsg){//显示收到消息
            msg.setReceiveState(true);
        }

        List<Map<String,Object>> responses = new ArrayList<>();
        // 定义两个指针，分别指向两个列表的头部
        int i = 0;
        int j = 0;
        // 循环遍历两个列表，将较小的元素添加到新列表中
        while (i < getMsg.size() && j < sendMsg.size()) {
            if (getMsg.get(i).getSendTime().compareTo(sendMsg.get(j).getSendTime()) < 0) {
                responses.add(getOneMessageResponse(getMsg.get(i++),true));
            } else {
                responses.add(getOneMessageResponse(sendMsg.get(j++),false));
            }
        }

        // 将剩余元素添加到新列表中
        while (i < getMsg.size()) {
            responses.add(getOneMessageResponse(getMsg.get(i++),true));
        }

        while (j < sendMsg.size()) {
            responses.add(getOneMessageResponse(sendMsg.get(j++),false));
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
    public Map<String,Object> getOneMessageResponse(PrivateMessage msg,boolean self){
        Map<String,Object> response;
        response= new HashMap<>();
        response.put("content",msg.getMessageContent());
        response.put("self",self);
        response.put("Datetime",msg.getSendTime());
        response.put("receiveState",msg.isReceiveState());
        return response;
    }

    public Integer getNameByID(String name){
        return mapper.getIDByName(name);
    }


}
