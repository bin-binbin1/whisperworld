package com.example.whisperworld.controller;

import com.example.whisperworld.service.groupService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class groupChatContoller extends TextWebSocketHandler {

    @Autowired
    private groupService service;

    @GetMapping("/send/getAllGroups")//获取用户全部群组
    public ResponseEntity<String>showGroups(@SessionAttribute("loginID") Integer userID){
        List<String>crowds = service.crowds(userID);
        ObjectMapper mapper = new ObjectMapper();
        String json="";
        try {
            json = mapper.writeValueAsString(crowds); // 将Map对象转换为JSON字符串
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(json, HttpStatus.OK);
    }





}
