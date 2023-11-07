package com.example.whisperworld.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper; // 引入jackson库
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.whisperworld.service.test_sample_s;
import com.example.whisperworld.entity.User;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
public class test_sample_c {

    @Autowired
    private test_sample_s userService;

    @GetMapping("/api/user/{id}")
    public ResponseEntity<String> getUser(@PathVariable Integer id) {
        System.out.println("successfully called");
        System.out.println("ok");

        User user = userService.getUser(id);
        if (user != null) {
            Map<String, Object> userData = new HashMap<>();
            userData.put("user_id", user.getUserID());
            userData.put("username", user.getUserName());
            userData.put("gender", user.getUserSex());
            userData.put("age", userService.yearsBetween(new Date(),user.getUserBDate()));
            userData.put("birthday", user.getUserBDate());
            userData.put("phone", user.getUserPhone());

            ObjectMapper mapper = new ObjectMapper();
            String json = "";
            try {
                json = mapper.writeValueAsString(userData); // 将Map对象转换为JSON字符串
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return new ResponseEntity<>(json, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }


    }
}
