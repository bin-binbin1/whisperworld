package com.example.whisperworld.controller;

import com.example.whisperworld.service.companionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jmx.support.ObjectNameManager;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class companionController {

    private companionService service;
    @Autowired
    public companionController(companionService service){
        this.service=service;
    }

    @GetMapping("/api/getAllFriends")
    public ResponseEntity<String> getAllFriends(Model model) {
        Integer userId=(Integer) model.getAttribute("loginID");
        List<String> names=service.getAllFriends(userId);
        List<Map<String,Object>> responses = new ArrayList<>();
        for(String name : names){
            Map<String,Object> response = new HashMap<>();
            response.put("friendNames",name);
            responses.add(response);
        }
        ObjectMapper mapper = new ObjectMapper();
        String json="";
        try {
            json = mapper.writeValueAsString(responses);
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(json, HttpStatus.OK);
    }
    @GetMapping("/api/getFriends/{name}")
    public ResponseEntity<String> getFriendByName(@PathVariable String prefix,Model model){
        Integer userId=(Integer) model.getAttribute("loginID");
        List<String> names=service.getFriendsByName(userId,prefix);
        List<Map<String,Object>> responses = new ArrayList<>();
        for(String name : names){
            Map<String,Object> response = new HashMap<>();
            response.put("friendNames",name);
            responses.add(response);
        }
        ObjectMapper mapper = new ObjectMapper();
        String json="";
        try {
            json = mapper.writeValueAsString(responses);
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(json, HttpStatus.OK);
    }

    @GetMapping("/api/getHistory/{name}")
    public ResponseEntity<String> getMessages(@PathVariable String name){

    }
}
