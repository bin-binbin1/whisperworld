package com.example.whisperworld.controller;

import com.example.whisperworld.entity.Notification;
import com.example.whisperworld.entity.TopicReplies;
import com.example.whisperworld.entity.Topics;
import com.example.whisperworld.service.topicService;
import com.example.whisperworld.specialClasses.topics;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.*;

@RestController
public class topicsController {
    @Autowired
    private topicService topicService;

    @GetMapping("/api/getTopics")//获取全部话题
    public ResponseEntity<String> showTopics(){
        List<topics> responses = topicService.showTopics(0,5);
        return new ResponseEntity<>(topicsToJson(responses), HttpStatus.OK);
    }
    @GetMapping("/api/getMoreTopics/{length}")
    public ResponseEntity<String> showMoreTopics(@PathVariable int length){
        List<topics> responses = topicService.showTopics(length,10);
        return new ResponseEntity<>(topicsToJson(responses), HttpStatus.OK);
    }
    @GetMapping("/api/getComments/{topicId}")//获取全部评论
    public ResponseEntity<String> showComments(@PathVariable Integer topicId){
        Topics topic = new Topics();
        topic.setTopicId(topicId);
        List<TopicReplies> topicReplies = topicService.showTopicReplies(topic);
        List<Map<String, Object>> responses = new ArrayList<>();
        // 遍历通知对象数组，并将每个对象的值添加到 Map 中
        for (TopicReplies topicReply : topicReplies) {
            Map<String, Object> response = new HashMap<>();
            response.put("commentId", topicReply.getCommentId());
            response.put("topicCommentusername", topicService.getNameByID(topicReply.getCommentUserId()));
            response.put("commentContent", topicReply.getCommentContent());
            response.put("commentTime", topicReply.getCommentTime());

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

    @PostMapping("/api/submitComment")//发布评论
    public ResponseEntity<String> sendCommend(@RequestBody TopicReplies topicreplie, @SessionAttribute("loginID") Integer loginID){
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
    public ResponseEntity<String> likeTopic(@RequestBody Topics topic, @SessionAttribute("loginID") Integer loginID){
        int response = topicService.likeTopic(topic,loginID);
        ObjectMapper mapper = new ObjectMapper();
        String json="";
        try {
            json = mapper.writeValueAsString(response); // 将Map对象转换为JSON字符串
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(json, HttpStatus.OK);
    }
    private String topicsToJson(List<topics> topics){//inverse list
        Collections.reverse(topics);
        ObjectMapper mapper = new ObjectMapper();
        String json="";
        try {
            json = mapper.writeValueAsString(topics); // 将Map对象转换为JSON字符串
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }
}
