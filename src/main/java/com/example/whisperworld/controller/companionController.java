package com.example.whisperworld.controller;

import com.example.whisperworld.service.companionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.session.Session;
import org.springframework.session.SessionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class companionController {
    @Autowired
    private companionService service;
    @Autowired
    private SessionRepository<? extends Session> sessionRepository;

    @GetMapping("/api/getCurrentID")
    public ResponseEntity<String> getCurrentID(@SessionAttribute("loginID") Integer userID){
        Map<String,Object> response = new HashMap<>();
        response.put("userID",userID);
        ObjectMapper mapper = new ObjectMapper();
        String json="";
        try {
            json = mapper.writeValueAsString(response); // 将Map对象转换为JSON字符串
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(json, HttpStatus.OK);
    }

    @MessageMapping("/getAllFriends")
    @SendTo("/response/friends")
    public String getAllFriends(StompHeaderAccessor accessor) {
        // 从session存储中获取session
        HttpSession session = (HttpSession) accessor.getSessionAttributes().get("httpSession");
        System.out.println("Session="+session);
        Integer loginID=(Integer) session.getAttribute("loginID");
        System.out.println("loginID="+loginID);
        List<String> names=service.getAllFriends(loginID);
        List<Map<String,Object>> responses = new ArrayList<>();
        for(String name : names){
            Map<String,Object> response = new HashMap<>();
            response.put("friendNames",name);
            responses.add(response);
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
    @MessageMapping("/getFriends/{prefix}")
    @SendTo("/response/friends")
    public String getFriendByName(@PathVariable String prefix,@SessionAttribute("loginID") Integer loginID){
        System.out.println("loginID"+loginID);
        List<String> names=service.getFriendsByName(loginID,prefix);
        List<Map<String,Object>> responses = new ArrayList<>();
        for(String name : names){
            Map<String,Object> response = new HashMap<>();
            response.put("friendNames",name);
            responses.add(response);
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

    @MessageMapping("/getHistory/{name}")
    @SendTo("/response/History")
    public String getMessages(@PathVariable String name, @SessionAttribute("loginID") Integer loginID, HttpSession session){
        return service.getMessages(loginID,name,session);
    }
    @MessageMapping("/sendMessages")
    @SendTo("/response/Msg")
    public String sendMessage(@Payload Map<String,Object> params, @ModelAttribute Integer userId, @ModelAttribute Integer friendId){
        Map<String,Object> response = new HashMap<>();
        response.put("send",service.sendMessage(params.get("messages").toString()
                , params.get("friendName").toString()
                ,userId
                ,friendId
        ));

        ObjectMapper mapper = new ObjectMapper();
        String json="";
        try {
            json = mapper.writeValueAsString(response);
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }
        return json;
    }

}
