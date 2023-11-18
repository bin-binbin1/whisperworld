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
    ObjectMapper Jsonmapper = new ObjectMapper();
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
    public boolean sendMessage(PrivateMessage msg){
        msg.setSendTime(new Date());
        msg.setMessageContentId(mapper.getMsgCount(msg));
        return mapper.insertMessage(msg)!=0;
    }
    public String getMessages(Integer userId, Integer receiverId){

        List<PrivateMessage> unReadMsg=mapper.getUnreadMsgs(userId,receiverId);
        mapper.setReceived(receiverId,userId);
        List<Map<String,Object>> responses = new ArrayList<>();
        List<PrivateMessage> fiveReadMsg=mapper.getMoreHistory(userId,receiverId,0,5);
        fiveReadMsg.addAll(unReadMsg);
        for (int i = fiveReadMsg.size() - 1; i >= 0; i--) {
            responses.add(getOneMessageResponse(fiveReadMsg.get(i),userId));
        }

        String json="";
        try {
            json = Jsonmapper.writeValueAsString(responses);
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }
        return json;
    }
    public Map<String,Object> getOneMessageResponse(PrivateMessage msg,Integer userID){
        boolean self= msg.getUserId().equals(userID);
        System.out.println(self);
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
        String json="";
        try {
            json = Jsonmapper.writeValueAsString(responses);
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
    public String getMoreHistory(Integer userID, String friendName,int start_length){
        Integer friendID=mapper.getIDByName(friendName);
        List<PrivateMessage> messages=mapper.getMoreHistory(userID,friendID,start_length,50);
        List<Map<String,Object>> responses = new ArrayList<>();
        for(int i=messages.size()-1;i>=0;i--){
            responses.add(getOneMessageResponse(messages.get(i),userID));
        }
        String json="";
        try {
            json = Jsonmapper.writeValueAsString(responses);
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }
        return json;
    }
}
