package com.example.whisperworld.controller;

import com.example.whisperworld.entity.Notification;
import com.example.whisperworld.service.homeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    private homeService service;
    @Autowired
    public homeController(homeService service){
        this.service=service;
    }

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
        ObjectMapper mapper = new ObjectMapper();
        String json="";
        try {
            json = mapper.writeValueAsString(responses); // 将Map对象转换为JSON字符串
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(json, HttpStatus.OK);

    }

    @PostMapping("/api/handleFriendRequest")
    public ResponseEntity<String> solveFriendRequests(@SessionAttribute("loginID") Integer userId, @RequestParam String data){
        System.out.println(data);
        ObjectMapper objectMapper = new ObjectMapper();
        String name="";
        String content="";
        try {
            JsonNode jsonNode = objectMapper.readTree(data);
            name = jsonNode.get("senderUsername").asText();
            content = jsonNode.get("decision").asText();
        } catch (JsonProcessingException e1){
            e1.printStackTrace();
        }
        return null;
    }
    @GetMapping("/api/getFriendRequests")
    public ResponseEntity<String> getFriendRequests(@SessionAttribute("loginID") Integer userId){


        List<Map<String, Object>> responses = new ArrayList<>();

        // 遍历通知对象数组，并将每个对象的值添加到 Map 中
        List<String> names=service.getAllRequestFriendName(userId);
        for (String name : names) {
            Map<String, Object> response = new HashMap<>();
            response.put("senderUsername",name);
            responses.add(response);
        }

        ObjectMapper mapper = new ObjectMapper();
        String json="";
        try {
            json = mapper.writeValueAsString(responses); // 将Map对象转换为JSON字符串
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(json, HttpStatus.OK);
    }

}
