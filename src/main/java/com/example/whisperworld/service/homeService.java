package com.example.whisperworld.service;

import com.example.whisperworld.entity.CrowdsMembers;
import com.example.whisperworld.entity.Friends;
import com.example.whisperworld.entity.Notification;
import com.example.whisperworld.mapper.homeMapper;
import com.example.whisperworld.specialClasses.groupApply;
import com.example.whisperworld.specialClasses.groupRequest;
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
        Integer friendId=mapper.getIDByName(friendName);
        if(state){
            mapper.setFriendPass(userId,friendId);
            if(mapper.setFriendPass(friendId,userId)==0){
                mapper.insertFriend(userId,friendId);
            }
            return true;
        }else{
            return mapper.deleteFriendApply(userId,friendId)==1;
        }
    }

    public List<groupRequest> getAllGroupRequest(Integer userId) {
        return mapper.getAllGroupRequest(userId);

    }

    public boolean setGroupApply(groupApply request) {

        if(request.isDecision()){

            return 1==mapper.setGroupMemberPass(request.getGroupId(),mapper.getIDByName(request.getUserName()));
        }
        return 1==mapper.setGroupMemberUnpass(request.getGroupId(),mapper.getIDByName(request.getUserName()));
    }
}
