package com.example.whisperworld.controller;

import com.example.whisperworld.entity.User;
import com.example.whisperworld.mapper.fwj_mapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.whisperworld.entity.*;
import com.example.whisperworld.service.fwj_service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class fwj_controller {

    @Autowired
    private fwj_service fwjService;


    @PostMapping("/register_submit2")
    public ResponseEntity<String> register(@RequestBody User user){
        Map<String,Object> response = new HashMap<>();
        if(fwjService.insertUser(user)>0){

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