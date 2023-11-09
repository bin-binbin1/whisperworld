package com.example.whisperworld.controller;

import com.example.whisperworld.entity.Notification;
import com.example.whisperworld.service.homeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping
    public ResponseEntity<String> Login(){
        List<Map<String, Object>> noticeMaps = new ArrayList<>();

        // 遍历通知对象数组，并将每个对象的值添加到 Map 中
        List<Notification> notices=service.getAllnotices();
        for (Notification notice : notices) {
            Map<String, Object> noticeMap = new HashMap<>();
            noticeMap.put("notificationId", notice.getNotificationId());
            noticeMap.put("notificationContent", notice.getNotificationContent());
            noticeMap.put("notificationTime", notice.getNotificationTime());

            noticeMaps.add(noticeMap);
        }
        ObjectMapper mapper = new ObjectMapper();
        String json="";
        try {
            json = mapper.writeValueAsString(noticeMaps); // 将Map对象转换为JSON字符串
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(json, HttpStatus.OK);

    }

}
