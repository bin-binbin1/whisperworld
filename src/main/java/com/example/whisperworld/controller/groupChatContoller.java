package com.example.whisperworld.controller;

import com.example.whisperworld.service.groupService;
import com.example.whisperworld.specialClasses.historyMsg;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.awt.*;
import java.security.Principal;
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
        if(crowds == null){
            System.out.println("null");
        }
        else{
            System.out.println("No");
        }

        messagingTemplate.convertAndSend(" /user/queue/"+userID+"/groups",service.namesToJSON(crowds,"groupNames"));
    }

    @MessageMapping("/getGroupMembers")//获取群成员
    public void showMembers(Principal principal, @RequestParam Integer groupId){
        System.out.println("获取群成员");
        Integer userID = Integer.parseInt(principal.getName());
        List<String>members = service.members(groupId);
        messagingTemplate.convertAndSend(" /user/queue/"+userID+"/groupMembers",service.namesToJSON(members,"groupName"));
    }

    @MessageMapping("/getGroupHistory")//获取群历史消息
    public void showHistory(Principal principal, @RequestParam Integer groupId){
        System.out.println("获取群历史消息");
        Integer userID = Integer.parseInt(principal.getName());
        List<historyMsg> historyMsgs = service.historyMsgs(groupId);
        for(historyMsg historyMsg : historyMsgs){
            if(historyMsg.getUserID() == userID){
                historyMsg.setSelf(true);
            }
            else{
                historyMsg.setSelf(false);
            }
        }
        ObjectMapper mapper = new ObjectMapper();
        String json="";
        try {
            json = mapper.writeValueAsString(historyMsgs);
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }
        messagingTemplate.convertAndSend(" /user/queue/"+userID+"/receiveHistory",json);
    }





}
