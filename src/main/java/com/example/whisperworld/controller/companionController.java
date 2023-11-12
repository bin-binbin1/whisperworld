package com.example.whisperworld.controller;

import com.example.whisperworld.service.companionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.session.Session;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;


import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
public class companionController extends TextWebSocketHandler {
    private final SimpMessagingTemplate messagingTemplate;
    private final Map<String, WebSocketSession> userSessions = new ConcurrentHashMap<>();
    private final Map<Integer,String> userIDToName = new ConcurrentHashMap<>();
    private final Map<String,Integer> nameToUser = new ConcurrentHashMap<>();
    @Autowired
    companionService service;
    public companionController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }
//    @Override
//    public void afterConnectionEstablished(WebSocketSession session) {
//        // 获取用户ID，这取决于你的应用程序如何处理用户身份
//        Principal principal=session.getPrincipal();
//        userSessions.put(principal.getName(), session);
//        Integer userID = Integer.parseInt(principal.getName());
//        userIDToName.put(userID,principal.getName());
//        nameToUser.put(principal.getName(),userID);
//        System.out.println("associateSession:"+principal.getName());
//    }

//    @GetMapping("/associateSession/{userId}")
//    public ResponseEntity<String> associateSession(@PathVariable Integer userId, Principal principal, HttpSession httpSession) {
//        WebSocketSession session = (WebSocketSession) httpSession.getAttribute("WEBSOCKET_SESSION");
//        System.out.println("Principal"+principal.getName());
////        //userSessions.put(principal.getName(),  session);
////        userIDToName.put(userID,principal.getName());
////        nameToUser.put(principal.getName(),userID);
////        System.out.println("associateSession:"+principal.getName());
//        return null;
//    }
//    @DeleteMapping("/api/removeSession/{userID}")
//    public ResponseEntity<String> removeSession(@PathVariable Integer  userID,Principal principal){
//        userSessions.remove(principal.getName());
//        userIDToName.remove(userID);
//        nameToUser.remove(principal.getName());
//        Map<String,Object> response = new HashMap<>();
//        response.put("userID",userID);
//        ObjectMapper mapper = new ObjectMapper();
//        String json="";
//        try {
//            json = mapper.writeValueAsString(response); // 将Map对象转换为JSON字符串
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//        return new ResponseEntity<>(json, HttpStatus.OK);
//    }

    @GetMapping("/api/getCurrentID")
    public ResponseEntity<String> getUserID(@SessionAttribute("loginID") Integer userID){
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
    public void getAllFriends(Principal principal) {

        // 从session存储中获取session

        Integer loginID=Integer.parseInt(principal.getName());

        List<String> names=service.getAllFriends(loginID);
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

        messagingTemplate.convertAndSend("/user/queue/"+loginID+"/friends",json);
    }
    @MessageMapping("/getFriends")
    public void getFriendByName(@RequestParam String prefix,Principal principal){
        Integer loginID = Integer.parseInt(principal.getName());
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

        messagingTemplate.convertAndSend("/user/queue/"+loginID+"/friends",json);
    }

    @MessageMapping("/getHistory")
    public void getMessages(@RequestParam String name, Principal principal){
        String json = service.getMessages(Integer.parseInt(principal.getName()),service.getNameByID(name));
        messagingTemplate.convertAndSend("/user/queue/"+principal.getName()+"/friends",json);
    }
//    @MessageMapping("/sendMessages")
//    public String sendMessage(@Payload Map<String,Object> params,Principal principal){
//        Map<String,Object> response = new HashMap<>();
//        response.put("send",service.sendMessage(params.get("messages").toString()
//                , params.get("friendName").toString()
//                ,userId
//                ,friendId
//        ));
//
//        ObjectMapper mapper = new ObjectMapper();
//        String json="";
//        try {
//            json = mapper.writeValueAsString(response);
//        }catch (JsonProcessingException e){
//            e.printStackTrace();
//        }
//        return json;
//    }

}
