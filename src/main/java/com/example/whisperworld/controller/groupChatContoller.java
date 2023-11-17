package com.example.whisperworld.controller;

import com.example.whisperworld.service.groupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.socket.handler.TextWebSocketHandler;

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
        List<String>crowds = service.crowds(userID);
        messagingTemplate.convertAndSend(" /user/queue/"+userID+"/groups",service.namesToJSON(crowds,"groupNames"));
    }

    @MessageMapping("/getGroupMembers")//获取群成员
    public void showMembers(Principal principal, @RequestParam Integer groupId){
        Integer userID = Integer.parseInt(principal.getName());
        List<String>members = service.members(groupId);
        messagingTemplate.convertAndSend(" /user/queue/"+userID+"/groupMembers",service.namesToJSON(members,"memberNames"));
    }





}
