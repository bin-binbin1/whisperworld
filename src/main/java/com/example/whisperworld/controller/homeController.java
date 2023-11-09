package com.example.whisperworld.controller;

import com.example.whisperworld.service.homeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class homeController {

    private homeService service;
    @Autowired
    public homeController(homeService service){
        this.service=service;
    }

    @GetMapping
    public ResponseEntity<String> Login()

}
