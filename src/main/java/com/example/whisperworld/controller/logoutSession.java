package com.example.whisperworld.controller;

import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

@SessionAttributes("{loginID},{loginName},{loginSex}")
@RestController
public class logoutSession {
    @GetMapping("/logout")
    public String get(ModelMap modelMap){//获取session
       String username = (String)modelMap.getAttribute("loginName");
       System.out.println("username is" + username);
       return "success";
    }
    @GetMapping("/delete")
    public String delete(SessionStatus sessionStatus){//清除session
        sessionStatus.setComplete();
        return "success";
    }
}
