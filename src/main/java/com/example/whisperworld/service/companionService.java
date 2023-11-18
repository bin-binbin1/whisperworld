package com.example.whisperworld.service;

import com.example.whisperworld.entity.Friends;
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

    public List<String> getPeopleNames(Integer userId,String prefix){
        return mapper.getPeopleByNAME(userId,prefix);
    }
    public List<String> getAllFriends(Integer userId){
        return mapper.getAllFriends(userId);
    }
    public List<String> getFriendsByName(Integer userId,String prefix){
        return mapper.getFriendsByNAME(userId, prefix);
    }
    public boolean friendApply(Integer userId,String friendName){
        Integer friendId= mapper.getIDByName(friendName);
        Friends friends=new Friends();
        friends.setFriendId(userId);
        friends.setUserId(friendId);
        return mapper.applyFriend(friends)==1;
    }
    public void deleteFriend(Integer userID,String username){
        Integer friendID=mapper.getIDByName(username);
        mapper.deleteMsgs(userID,friendID);
        mapper.deleteMsgs(friendID,userID);
        mapper.deleteFriends(userID,friendID);
        mapper.deleteFriends(friendID,userID);
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

        mapper.setReceived(receiverId,userId);
        List<PrivateMessage> getMsg=mapper.getMessagesFromA2B(receiverId,userId);
        List<PrivateMessage> sendMsg=mapper.getMessagesFromA2B(userId,receiverId);

        List<Map<String,Object>> responses = new ArrayList<>();
        // 定义两个指针，分别指向两个列表的头部
        int i = 0;
        int j = 0;
        // 循环遍历两个列表，将较小的元素添加到新列表中
        while (i < getMsg.size() && j < sendMsg.size()) {
            if (getMsg.get(i).getSendTime().compareTo(sendMsg.get(j).getSendTime()) < 0) {
                responses.add(getOneMessageResponse(getMsg.get(i++),false));
            } else {
                responses.add(getOneMessageResponse(sendMsg.get(j++),true));
            }
        }

        // 将剩余元素添加到新列表中
        while (i < getMsg.size()) {
            responses.add(getOneMessageResponse(getMsg.get(i++),false));
        }

        while (j < sendMsg.size()) {
            responses.add(getOneMessageResponse(sendMsg.get(j++),true));
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
        response.put("time",msg.getSendTime());
        response.put("receiveState",msg.isReceiveState());
        return response;
    }

    public Integer getNameByID(String name){
        return mapper.getIDByName(name);
    }

    public String namesToJSON(List<String> names){
        List<Map<String,Object>> responses = new ArrayList<>();
        for(String name : names){
            Map<String,Object> response = new HashMap<>();
            response.put("friendNames",name);
            responses.add(response);
            System.out.println("friendsname"+name);
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
    public void setReceived(Integer userID,Integer friendID){//user receive the msg
        mapper.setReceived(friendID,userID);
    }
    public String getNameByID(Integer userID){
        return mapper.getNameByID(userID);
    }
}
