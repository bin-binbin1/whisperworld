package com.example.whisperworld.service;

import com.example.whisperworld.entity.Friends;
import com.example.whisperworld.entity.Notification;
import com.example.whisperworld.mapper.homeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class homeService {
    private homeMapper mapper;

    @Autowired
    public homeService(homeMapper mapper){
        this.mapper=mapper;
    }
    public List<Notification> getAllnotices(){
        return mapper.getAllNotice();
    }

    public List<String> getAllRequestFriendName(Integer userId){
        return mapper.getFriendNames(userId);
    }

    public boolean setFriendApply(Integer userId,String friendName,boolean state){

        Friends friends = new Friends();
        friends.setFriendId(mapper.getIDByName(friendName));
        friends.setUserId(userId);
        if(state){
            return mapper.setFriendPass(friends)+mapper.insertFriend(friends)==2;
        }else{
            return mapper.deleteFriendApply(friends)==1;
        }
    }
}
