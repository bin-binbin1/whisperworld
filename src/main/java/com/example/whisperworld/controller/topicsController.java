package com.example.whisperworld.controller;

import com.example.whisperworld.entity.TopicReplies;
import com.example.whisperworld.entity.Topics;
import com.example.whisperworld.service.topicService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.listener.Topic;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class topicsController {
    @Autowired
    private topicService topicService;

    @GetMapping("/api/getTopics")//获取全部话题
    public ResponseEntity<String> showTopics(){
        List<Topics>response = topicService.showTopics();
        ObjectMapper mapper = new ObjectMapper();
        String json="";
        try {
            json = mapper.writeValueAsString(response); // 将Map对象转换为JSON字符串
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(json, HttpStatus.OK);
    }
    @GetMapping("/api/getComments")//获取全部评论
    public ResponseEntity<String> showComments(@RequestBody Topics topic){
        List<TopicReplies>response = topicService.showTopicReplies(topic);
        ObjectMapper mapper = new ObjectMapper();
        String json="";
        try {
            json = mapper.writeValueAsString(response); // 将Map对象转换为JSON字符串
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(json, HttpStatus.OK);
    }


    @PostMapping("/api/sendtopic")//发布话题
    public ResponseEntity<String> sendTopic(@RequestBody Topics topic, @SessionAttribute("loginID") Integer loginID){
        topic.setUserId(loginID);
        boolean response = topicService.postTopic(topic);
        ObjectMapper mapper = new ObjectMapper();
        String json="";
        try {
            json = mapper.writeValueAsString(response); // 将Map对象转换为JSON字符串
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(json, HttpStatus.OK);
    }

    @PostMapping("/api/sendcomment")//发布评论
    public ResponseEntity<String> sendCommend(@RequestBody TopicReplies topicreplie, @SessionAttribute("loginID") Integer loginID){
        //前端传递 topicId
        topicreplie.setCommentUserId(loginID);
        boolean response = topicService.postComment(topicreplie);
        ObjectMapper mapper = new ObjectMapper();
        String json="";
        try {
            json = mapper.writeValueAsString(response); // 将Map对象转换为JSON字符串
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(json, HttpStatus.OK);
    }


    @PostMapping("/api/likeTopic")//点赞
    public ResponseEntity<String> likeTopic(@RequestBody Topics topic){
        int response = topicService.likeTopic(topic);
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
