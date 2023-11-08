package com.example.whisperworld.controller;

import com.example.whisperworld.entity.User;
import com.example.whisperworld.mapper.login_mapper;
import com.example.whisperworld.service.login_service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class login_controller {

    login_service loginService;
    @Autowired
    public login_controller(login_service loginService){
        this.loginService=loginService;
    }
    @PostMapping("/login")
    public ResponseEntity<String> Login(@RequestBody User user){
        boolean authenticated = loginService.userExist(user);
        System.out.println(user);
        Map<String,Object> response = new HashMap<>();
        response.put("authenticated",authenticated);
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
