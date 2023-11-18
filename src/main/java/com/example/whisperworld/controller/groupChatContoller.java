package com.example.whisperworld.controller;

import com.example.whisperworld.entity.CrowdsMessage;
import com.example.whisperworld.service.groupService;
import com.example.whisperworld.specialClasses.historyMsg;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.awt.*;
import java.io.IOException;
import java.security.Principal;
import java.util.Date;
import java.util.List;

@RestController
public class groupChatContoller extends TextWebSocketHandler {

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    private groupService service;
    public groupChatContoller(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/getAllGroups")//获取用户全部群组
    public void showGroups(Principal principal){
        System.out.println("获取群组");
        Integer userID = Integer.parseInt(principal.getName());
        System.out.println("userId.getName:"+userID);
        List<String>crowds = service.crowds(userID);
        System.out.println(crowds);
        if(crowds == null){
            System.out.println("null");
        }
        else{
            System.out.println("No");
        }
        messagingTemplate.convertAndSend("/user/queue/"+userID+"/groups",service.namesToJSON(crowds,"groupName"));
    }

    @MessageMapping("/getGroupMembers")//获取群成员
    public void showMembers(Principal principal, @RequestParam Integer groupId){
        System.out.println("获取群成员");
        System.out.println(groupId);
        Integer userID = Integer.parseInt(principal.getName());
        List<String>members = service.members(groupId);
        messagingTemplate.convertAndSend("/user/queue/"+userID+"/groupMembers",service.namesToJSON(members,"groupMember"));
    }

    @MessageMapping("/getGroupHistory")//获取群历史消息
    public void showHistory(Principal principal, @RequestParam Integer groupId,@RequestParam Integer num){
        System.out.println("获取群历史消息");
        Integer userID = Integer.parseInt(principal.getName());
        List<historyMsg> historyMsgs = service.historyMsgs(groupId,num);
        for(historyMsg historyMsg : historyMsgs){
            if(historyMsg.getUserID() == userID){
                historyMsg.setSelf(true);
            }
            else{
                historyMsg.setSelf(false);
            }
            historyMsg.setUserID(null);
        }
        ObjectMapper mapper = new ObjectMapper();
        String json="";
        try {
            json = mapper.writeValueAsString(historyMsgs);
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }
        messagingTemplate.convertAndSend("/user/queue/"+userID+"/receiveHistory",json);
    }

    @MessageMapping("/groupMsg")//发送消息
    public void sendMessage(Principal principal,@Payload String jsonData){
        System.out.println("发送消息");
        try {
            // 将JSON字符串映射到crowdsMessage对象
            ObjectMapper objectMapper = new ObjectMapper();
            CrowdsMessage crowdsMessage = objectMapper.readValue(jsonData, CrowdsMessage.class);
            Integer userID = Integer.parseInt(principal.getName());
            crowdsMessage.setUserId(userID);
            crowdsMessage.setSendTime(new Date());
            Boolean response = true;
            ObjectMapper mapper = new ObjectMapper();
            String json="";
            try {
                json = mapper.writeValueAsString(response);
            }catch (JsonProcessingException e){
                e.printStackTrace();
            }
            messagingTemplate.convertAndSend("/user/queue/"+userID+"/groupMsg",json);

        } catch (IOException e) {
            e.printStackTrace();
            // 处理JSON解析异常
        }



    }



}
