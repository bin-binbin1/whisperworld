package com.example.whisperworld.controller;

import com.example.whisperworld.entity.CrowdsMessage;
import com.example.whisperworld.service.groupService;
import com.example.whisperworld.specialClasses.groups;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
        List<groups>crowds = service.crowds(userID);
        System.out.println(crowds);
        ObjectMapper mapper = new ObjectMapper();
        String json="";
        try {
            json = mapper.writeValueAsString(crowds);
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }
        messagingTemplate.convertAndSend("/user/queue/groups/"+userID,json);
    }

    @MessageMapping("/getGroupMembers")//获取群成员
    public void showMembers(Principal principal, @RequestParam Integer groupId){
        System.out.println("获取群成员");
        Integer userID = Integer.parseInt(principal.getName());
        List<String>members = service.members(groupId);
        System.out.println(members);
        messagingTemplate.convertAndSend("/user/queue/groupMembers/"+userID,service.namesToJSON(members,"userName"));
    }

    @MessageMapping("/getGroupHistory")//获取群历史消息
    public void showHistory(Principal principal, @RequestParam Integer groupId,@RequestParam Integer num){
        System.out.println("获取群历史消息");
        System.out.println(num);
        Integer userID = Integer.parseInt(principal.getName());
        List<historyMsg> historyMsgs = service.historyMsgs(userID,groupId,num);
        System.out.println(historyMsgs);
        ObjectMapper mapper = new ObjectMapper();
        String json="";
        try {
            json = mapper.writeValueAsString(historyMsgs);
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }
        messagingTemplate.convertAndSend("/user/queue/receiveHistory/"+userID,json);
    }

    @MessageMapping("/groupMsg")//发送消息
    public void sendMessage(Principal principal,@RequestParam String jsonData){
        System.out.println("发送消息");
        try {
            // 将JSON字符串映射到crowdsMessage对象
            ObjectMapper objectMapper = new ObjectMapper();
            CrowdsMessage crowdsMessage = objectMapper.readValue(jsonData, CrowdsMessage.class);
            Integer userID = Integer.parseInt(principal.getName());
            System.out.println(crowdsMessage.getMessageContent());
            System.out.println(crowdsMessage.getGroupId());

            crowdsMessage.setUserId(userID);
            crowdsMessage.setSendTime(new Date());

            Boolean send = service.message(crowdsMessage);
            List<Integer> others = service.toOthers(userID);
            if(!send) {//发送失败
                return;
            }
            ObjectMapper mapper = new ObjectMapper();
            String json="";
            try {
                json = mapper.writeValueAsString(send);
            }catch (JsonProcessingException e){
                e.printStackTrace();
            }
            messagingTemplate.convertAndSend("/user/queue/groupMsg/"+userID,json);//发送给自己

        } catch (IOException e) {
            e.printStackTrace();
            // 处理JSON解析异常
        }



    }



}
