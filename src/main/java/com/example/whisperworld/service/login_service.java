package com.example.whisperworld.service;

import com.example.whisperworld.entity.User;
import com.example.whisperworld.mapper.login_mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpSession;
import java.awt.desktop.SystemEventListener;

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
    public String sessionLogin(HttpSession session, User user){
        user.setUserID(loginMapper.login_id(user.getUserName()));
        session.setAttribute("loginID",user.getUserID());
        return "success";
    }

}
