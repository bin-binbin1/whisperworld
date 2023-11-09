package com.example.whisperworld.controller;

import com.example.whisperworld.entity.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.whisperworld.service.registerService;

import java.util.HashMap;
import java.util.Map;

@RestController
public class registerPage {

    @Autowired
    private registerService registerService;


    @PostMapping("/register_submit2")
    public ResponseEntity<String> register(@RequestBody User user){
        Map<String,Object> response = new HashMap<>();
        if(registerService.insertUser(user)>0){
            response.put("success",true);
        }else{
            response.put("success",false);
        }
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