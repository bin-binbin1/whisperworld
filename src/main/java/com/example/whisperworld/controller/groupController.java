package com.example.whisperworld.controller;

import com.example.whisperworld.entity.Crowds;
import com.example.whisperworld.service.groupService;
import com.example.whisperworld.service.groupsService;
import com.example.whisperworld.specialClasses.crowdCreate;
import com.example.whisperworld.specialClasses.crowdRequest;
import com.example.whisperworld.specialClasses.groups;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.swing.*;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
public class groupController extends TextWebSocketHandler {

    @Autowired
    private groupsService service;
    private ObjectMapper mapper = new ObjectMapper();

    @GetMapping("/api/currentUserId")//获取用户ID
    public ResponseEntity userId(@SessionAttribute("loginID") Integer userID){
        String json="";
        try {
            json = mapper.writeValueAsString(userID); // 将Map对象转换为JSON字符串
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(json, HttpStatus.OK);
    }
    @GetMapping("/api/getAllgroups")//获取全部群组
    public ResponseEntity<String> showGroups(@SessionAttribute("loginID") Integer userID){
        System.out.println("获取群组");
        System.out.println("userId:"+userID);
        List<Map<String,Object>> crowds = service.groups(userID);
        System.out.println(crowds);
        String json="";
        try {
            json = mapper.writeValueAsString(crowds); // 将Map对象转换为JSON字符串
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(json, HttpStatus.OK);
    }
    @GetMapping("/api/searchGroups")//检索群聊
    public ResponseEntity<String> searchGroups(@RequestParam("searchInput") String searchInput){
        System.out.println("检索群聊");
        System.out.println("输入："+searchInput);
        if(searchInput == ""){
            searchInput = " ";
        }
        List<Map<String,Object>> groups = service.searchGroups(searchInput);
        System.out.println(groups);
        System.out.println(groups);
        String json="";
        try {
            json = mapper.writeValueAsString(groups); // 将Map对象转换为JSON字符串
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(json, HttpStatus.OK);
    }
    @PostMapping("/api/createGroup")//创建群聊
    public ResponseEntity<String> createGroups(@RequestBody crowdCreate crowdCreate, @SessionAttribute("loginID") Integer userID){
        System.out.println("创建群聊");
        System.out.println("群名："+crowdCreate.getGroupName());
        System.out.println("群公告："+crowdCreate.getGroupIntro());
        Crowds crowds = new Crowds();
        crowds.setGroupName(crowdCreate.getGroupName());
        crowds.setMasterId(userID);
        crowds.setManagerId(userID);
        crowds.setGroupNum(1);
        crowds.setCreateDate(new Date());
        crowds.setGroupBackground(crowdCreate.getGroupIntro());
        Boolean response = service.createGroups(crowds);
        String json="";
        try {
            json = mapper.writeValueAsString(response); // 将Map对象转换为JSON字符串
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(json, HttpStatus.OK);

    }

    @PostMapping("/api/dismissGroup")//解散群聊
    public ResponseEntity<String> dismissGroup(@RequestBody Crowds crowd){
        System.out.println("解散群聊");
        System.out.println(crowd.getGroupId());
        Boolean response = service.dismissCrowd(crowd.getGroupId());
        String json="";
        try {
            json = mapper.writeValueAsString(response); // 将Map对象转换为JSON字符串
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(json, HttpStatus.OK);
    }

    @PostMapping("/api/leaveGroup")//退出群聊
    public ResponseEntity<String> leaveGroup(@RequestBody Crowds crowd,@SessionAttribute("loginID") Integer userID){
        System.out.println("退出群聊");
        System.out.println(crowd.getGroupId());
        Boolean response = service.leaveGroup(crowd.getGroupId(),userID);
        String json="";
        try {
            json = mapper.writeValueAsString(response); // 将Map对象转换为JSON字符串
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(json, HttpStatus.OK);
    }

}
