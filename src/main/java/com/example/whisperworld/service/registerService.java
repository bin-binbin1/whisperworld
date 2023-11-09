package com.example.whisperworld.service;

import com.example.whisperworld.entity.User;
import com.example.whisperworld.mapper.registerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class registerService {

    private int id;

    private final registerMapper Mapper;
    @Autowired
    public registerService(registerMapper mapper){
        this.Mapper=mapper;
        id=Mapper.getUserNum();

    }
    public int insertUser(User user) {
        System.out.println(user);
        if(Mapper.getSameNameCount(user)>0)
            return 0;

        user.setUserState("Online");
        user.setUserLevel(1);
        user.setUrDate(new Date());

        return insertUser_(user);
    }
    private synchronized int insertUser_(User user){
        user.setUserID(id);
        int len=Mapper.insertUser(user);
        id++;
        return len;
    }
    

}
