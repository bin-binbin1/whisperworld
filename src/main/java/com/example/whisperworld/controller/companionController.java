package com.example.whisperworld.controller;

import com.example.whisperworld.entity.PrivateMessage;
import com.example.whisperworld.entity.User;
import com.example.whisperworld.service.companionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;


import java.security.Principal;
import java.util.*;
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
    @GetMapping("/api/getCurrentID")
    public ResponseEntity<String> getUserID(@SessionAttribute("loginID") Integer userID){
        System.out.println("userID:"+userID.toString());
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
    @GetMapping("/api/searchFriends/{prefix}")
    public ResponseEntity<String> getOtherPeople(@SessionAttribute("loginID") Integer userID, @PathVariable String prefix){
        List<String> names=service.getPeopleNames(userID,prefix);
        return ResponseEntity.ok(service.namesToJSON(names));
    }
    @PostMapping("/api/friendApply")
    public ResponseEntity<String> friendApply(@SessionAttribute("loginID") Integer userID,@RequestBody User user){
        return ResponseEntity.ok(""+service.friendApply(userID, user.getUserName()));
    }
    @GetMapping("/api/deleteFriend/{friendName}")
    public ResponseEntity<String> deleteFriend(@SessionAttribute("loginID") Integer userId,@PathVariable String friendName){
        service.deleteFriend(userId,friendName);
        return ResponseEntity.ok("success");
    }
    @MessageMapping("/getAllFriends")
    public void getAllFriends(Principal principal) {
        Integer loginID=Integer.parseInt(principal.getName());
        List<String> names=service.getAllFriends(loginID);
        messagingTemplate.convertAndSend("/user/queue/friends/"+loginID,service.namesToJSON(names));//Map<name,WebsocketSession>
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

        messagingTemplate.convertAndSend("/user/queue/friends/"+loginID,json);
    }

    @MessageMapping("/getHistory")
    public void getMessages(@RequestParam String name, Principal principal){
        Integer friendID=service.getNameByID(name);
        String json = service.getMessages(Integer.parseInt(principal.getName()),friendID);
        messagingTemplate.convertAndSend("/user/queue/History/"+principal.getName(),json);
        messagingTemplate.convertAndSend("/user/queue/setState/"+friendID,name+"2");
    }
    @MessageMapping("/sendMessages")
    public void sendMessage(@RequestParam String message, Principal principal){
        ObjectMapper objectMapper = new ObjectMapper();
        String name="";
        String content="";
        try {
            JsonNode jsonNode = objectMapper.readTree(message);
            name = jsonNode.get("friendName").asText();
            content = jsonNode.get("messageContent").asText();
        } catch (JsonProcessingException e1){
            e1.printStackTrace();
        }
        Integer friendId = service.getNameByID(name);
        Integer userId =Integer.parseInt(principal.getName());
        if(!service.sendMessage(content
                ,userId
                ,friendId
        )){
            return;
        }
        PrivateMessage privateMessage = new PrivateMessage();
        privateMessage.setMessageContent(content);
        privateMessage.setSendTime(new Date());
        Map<String,Object> response = service.getOneMessageResponse(privateMessage,true);
        response.put("friendName",name);
        ObjectMapper mapper = new ObjectMapper();
        String json="";
        try {
            json = mapper.writeValueAsString(response);
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }
        messagingTemplate.convertAndSend("/user/queue/Msg/"+principal.getName(),json);//发送给自己
        response = service.getOneMessageResponse(privateMessage,false);
        response.put("friendName",service.getNameByID(userId));
        try {
            json = mapper.writeValueAsString(response);
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }

        messagingTemplate.convertAndSend("/user/queue/Msg/"+friendId,json);//发送给好友

    }
    @MessageMapping("/receivedMsg")
    public void receivedMsg(@RequestParam String friendName,Principal principal){//告诉朋友收到了消息
        Integer friendID=service.getNameByID(friendName);
        Integer userID=Integer.parseInt( principal.getName());
        service.setReceived(userID,friendID);
        System.out.println("/user/queue/setState/"+friendID);
        messagingTemplate.convertAndSend("/user/queue/setState/"+friendID,service.getNameByID(userID)+"1");
    }
}
