package com.example.whisperworld.service;

import com.example.whisperworld.entity.User;
import com.example.whisperworld.mapper.login_mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@SessionAttributes({"loginID","loginName","loginSex"})
@Service
public class login_service {

    private final login_mapper loginMapper;
    @Autowired
    public login_service(login_mapper loginMapper){
        this.loginMapper = loginMapper;
    }

    public boolean userExist(User user){
        String pwd =loginMapper.login_pwd(user.getUserName());
        return pwd.equals(user.getUserPassword());
    }
    public String sessionLogin(User user, Model model){
        model.addAttribute("loginID",user.getUserID());
        model.addAttribute("loginName",user.getUserName());
        model.addAttribute("loginSex", user.getUserSex());
        System.out.println("IDservice:" + user.getUserID());
        System.out.println("testID:" + model.getAttribute("loginID"));
        System.out.println("testName:" + model.getAttribute("loginName"));
        System.out.println("testSex:" + model.getAttribute("loginSex"));
        return "success";
    }
}
