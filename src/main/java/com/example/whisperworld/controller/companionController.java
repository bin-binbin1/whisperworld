package com.example.whisperworld.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;


import java.util.HashMap;
import java.util.Map;

@RestController
public class companionController {

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
}
