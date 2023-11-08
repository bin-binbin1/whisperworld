package com.example.whisperworld.service;

import com.example.whisperworld.entity.User;
import com.example.whisperworld.mapper.login_mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;


@Service
//@SessionAttributes({"user"})
public class login_service {

    private final login_mapper loginMapper;
    @Autowired
    public login_service(login_mapper login){
        this.loginMapper = login;
    }
    @RequestMapping("/userExist")
    public boolean userExist(User user){
        String pwd =loginMapper.login_pwd(user.getUserName());
     return pwd == user.getUserPassword();

    }

}
