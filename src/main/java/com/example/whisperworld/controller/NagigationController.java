package com.example.whisperworld.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NagigationController {
    @GetMapping(value = {"/welcome","/"})
    public String welcome(Model model) {
        model.addAttribute("name", "World");
        return "public/welcome";
    }

    @GetMapping("/login")
    public String login(Model model){
        //处理 session
        return "public/login";
    }

    @GetMapping("/register")
    public String register(Model model){
        return "public/register";
    }

    @GetMapping("/home")
    public String home(Model model){
        return "home";
    }

    @GetMapping("/companion")
    public String companion(Model model){
        return "companion";
    }

    @GetMapping("/topics")
    public String topics(Model model){
        return "topics";
    }

    @GetMapping("/group")
    public String group(Model model){
        return "group";
    }

    @GetMapping("/profile")
    public String profile(Model model){
        return "profile";
    }

    @GetMapping("/logout")
    public String logout(Model model){
        // 处理session

        return "public/logout";

    }


}

