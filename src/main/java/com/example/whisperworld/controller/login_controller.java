package com.example.whisperworld.controller;

import com.example.whisperworld.entity.User;
import com.example.whisperworld.service.login_service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@SessionAttributes({"loginID","loginName","loginSex"})
@RestController
public class login_controller {

    login_service loginService;
    @Autowired
    public login_controller(login_service loginService){
        this.loginService=loginService;
    }
    @PostMapping("/login/submit")
    public ResponseEntity<String> Login(@RequestBody User user, ModelMap modelMap){
        boolean authenticated = loginService.userExist(user);
        System.out.println(user);
        if(authenticated){
            modelMap.addAttribute("loginID",user.getUserID());
            modelMap.addAttribute("loginName",user.getUserName());
            modelMap.addAttribute("loginSex", user.getUserSex());
        }
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
