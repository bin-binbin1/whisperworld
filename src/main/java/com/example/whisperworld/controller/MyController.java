package com.example.whisperworld.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MyController {
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("name", "World");
        return "public/index";
    }

    @GetMapping("/loginpage")
    public String test(Model model){
        return "test";
    }

    @PostMapping("/login")
    public String doLogin(@RequestParam("username") String username, @RequestParam("password") String password, Model model) {
        // 在这里添加你的登录逻辑
        // ...
        return "home";
    }
}

