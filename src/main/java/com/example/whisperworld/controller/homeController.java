package com.example.whisperworld.controller;

import com.example.whisperworld.entity.Notification;
import com.example.whisperworld.service.homeService;
import com.example.whisperworld.specialClasses.friendRequest;
import com.example.whisperworld.specialClasses.groupApply;
import com.example.whisperworld.specialClasses.groupRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class homeController {

    private final homeService service;
    @Autowired
    public homeController(homeService service){
        this.service=service;
    }
    private ObjectMapper mapper = new ObjectMapper();
    @GetMapping("/api/getNotices")
    public ResponseEntity<String> Login(){
        List<Map<String, Object>> responses = new ArrayList<>();

        // 遍历通知对象数组，并将每个对象的值添加到 Map 中
        List<Notification> notices=service.getAllnotices();
        for (Notification notice : notices) {
            Map<String, Object> response = new HashMap<>();
            response.put("notificationId", notice.getNotificationId());
            response.put("notificationContent", notice.getNotificationContent());
            response.put("notificationTime", notice.getNotificationTime());

            responses.add(response);
        }
        String json="";
        try {
            json = mapper.writeValueAsString(responses); // 将Map对象转换为JSON字符串
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(json, HttpStatus.OK);

    }

    @PostMapping("/api/handleFriendRequest")
    public ResponseEntity<String> solveFriendRequests(@SessionAttribute("loginID") Integer userId, @RequestBody friendRequest request){
        boolean result= service.setFriendApply(userId,request.getSenderUsername(),request.isDecision());
        return  ResponseEntity.ok(""+result);

    }
    @GetMapping("/api/getFriendRequests")
    public ResponseEntity<String> getFriendRequests(@SessionAttribute("loginID") Integer userId){
        List<Map<String, Object>> responses = new ArrayList<>();
        List<String> names=service.getAllRequestFriendName(userId);
        for (String name : names) {
            Map<String, Object> response = new HashMap<>();
            response.put("senderUsername",name);
            responses.add(response);
        }
        String json="";
        try {
            json = mapper.writeValueAsString(responses); // 将Map对象转换为JSON字符串
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(json, HttpStatus.OK);
    }
    @GetMapping("/api/getGroupRequests")
    public ResponseEntity<String> getGroupRequests(@SessionAttribute("loginID") Integer userId){
        List<groupRequest> names=service.getAllGroupRequest(userId);
        String json="";
        try {
            json = mapper.writeValueAsString(names); // 将Map对象转换为JSON字符串
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(json);
    }
    @PostMapping("/api/handleGroupRequest")
    public ResponseEntity<String> handleGroupRequest(@RequestBody groupApply request){
        boolean result=service.setGroupApply(request);
        return ResponseEntity.ok(""+result);
    }
}
