package com.example.whisperworld.controller;

import com.example.whisperworld.entity.Novel;
import com.example.whisperworld.entity.bookmark;
import com.example.whisperworld.entity.bookmarks;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.whisperworld.service.novelService;

import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class novelController {
    novelService service;
    @Autowired
    public novelController(novelService service){
        this.service=service;
    }
    @GetMapping("/novel/getAll")
    public ResponseEntity getAllnovels(){
        List<Novel> novel= service.getNovels();
        ObjectMapper mapper=new ObjectMapper();
        String json="";
        try{
            json = mapper.writeValueAsString(novel);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(json);
    }
    @GetMapping("/novel/content/{id}/{chapter}/{page}")
    public ResponseEntity getNovelContentById(@PathVariable int id
            ,@PathVariable int chapter
            ,@PathVariable int page) throws IOException {
        String content = service.getContent(id,chapter,page);
        Map<String,Object> m=new HashMap<>();
        m.put("content",content);
        ObjectMapper mapper = new ObjectMapper();
        String json="";
        try {
            json = mapper.writeValueAsString(m); // 将Map对象转换为JSON字符串
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(json, HttpStatus.OK);
    }
    @GetMapping("/novel/getBookmark")
    public ResponseEntity getBookmark(Principal principal){
        List<bookmark> mark=service.getBookmark(principal.getName());
        ObjectMapper mapper = new ObjectMapper();
        String json="";
        try{
            json  = mapper.writeValueAsString(mark);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(json);
    }
    @PostMapping("/novel/addBookmark")
    public ResponseEntity addBookmark(@RequestBody bookmarks marks){
        service.addmark(marks);

        return ResponseEntity.ok("");
    }

}
