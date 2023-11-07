package com.example.whisperworld.service;

import com.example.whisperworld.entity.User;
import com.example.whisperworld.mapper.fwj_mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class fwj_service {

    private int id;

    private final fwj_mapper fwjMapper;
    @Autowired
    public fwj_service(fwj_mapper fwjMapper){
        this.fwjMapper=fwjMapper;
        id=fwjMapper.getUserNum();

    }
    public int insertUser(User user) {
        if(fwjMapper.getSameNameCount(user)>0)
            return 0;

        user.setUserState("Online");
        user.setUserLevel(1);
        user.setUrDate(new Date());

        return insertUser_(user);
    }
    private synchronized int insertUser_(User user){
        user.setUserID(id);
        int len=fwjMapper.insertUser(user);
        id++;
        return len;
    }

}
