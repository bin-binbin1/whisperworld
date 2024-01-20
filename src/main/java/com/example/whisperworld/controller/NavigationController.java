package com.example.whisperworld.controller;

import com.example.whisperworld.mapper.login_mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class NavigationController {
    login_mapper mapper;
    @Autowired
    public NavigationController(login_mapper mapper){
        this.mapper = mapper;
    }
    @GetMapping(value = {"/welcome","/"})
    public String welcome(Model model) {
        model.addAttribute("name", "World");
        return "forward:/public/welcome.html";
    }
    @GetMapping("/public/help")
    public String help(Model model){
        return "forward:/public/help.html";
    }
    @GetMapping("/login")
    public String login(Model model){
        //处理 session
        return "forward:/public/login.html";
    }

    @GetMapping("/public/register")
    public String register(Model model){
        return "forward:/public/register.html";
    }

    @GetMapping("/home")
    public String home(Model model){
        return "forward:/protected/home.html";
    }

    @GetMapping("/companion")
    public String companion(Model model){
        return "forward:/protected/companion.html";
    }
    @GetMapping("/chat")
    public String chat(Model model){return "forward:/protected/chat.html";}

    @GetMapping("/topics")
    public String topics(Model model){
        return "forward:/protected/topics.html";
    }

    @GetMapping("/group")
    public String group(Model model){
        return "forward:/protected/group.html";
    }

    @GetMapping("/profile")
    public String profile(Model model){
        return "forward:/protected/profile.html";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        Integer loginId = (Integer)session.getAttribute("loginID");
        mapper.offlineState(loginId);
        //将状态设置为登出
        session.invalidate();//清空session
        return "forward:/public/welcome.html";
    }
    @GetMapping("/library")
    public String novel(Model model){
        return "forward:/protected/library.html";
    }

    @GetMapping("/asdfsdarfsrawerawrwaerwer")
    public String admin(Model model){
        return "forward:/private/admin.html";
    }

}

