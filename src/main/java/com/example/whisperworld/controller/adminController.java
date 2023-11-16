package com.example.whisperworld.controller;

import com.example.whisperworld.entity.Notification;
import com.example.whisperworld.service.adminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class adminController {
    private final adminService service;
    @Autowired
    public adminController(adminService service){
        this.service=service;
    }
    @GetMapping("/admin_announce/topics/{topicId}")
    public void deleteTopic(@SessionAttribute("loginID") Integer loginID, @PathVariable Integer topicId){
        service.deleteTopic(topicId,loginID);
    }
    @PostMapping("/admin_announce/post_note")
    public ResponseEntity<String> postTopic(@SessionAttribute("loginID") Integer loginID, @RequestBody Notification notification){
        service.postNote(loginID,notification);
        return ResponseEntity.ok("success");
    }
    @GetMapping("/admin_announce/topicsAll")
    public ResponseEntity<String> getAllTopics(){
        return ResponseEntity.ok(service.getllTopics());
    }
}
