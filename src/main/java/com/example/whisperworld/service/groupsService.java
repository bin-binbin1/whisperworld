package com.example.whisperworld.service;

import com.example.whisperworld.entity.Crowds;
import com.example.whisperworld.mapper.groupMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Block;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class groupsService {
    private final groupMapper groupMapper;

    @Autowired
    public groupsService( groupMapper groupMapper){
        this.groupMapper = groupMapper;
    }

    public List<Map<String,Object>> groups(Integer userID){//查询用户所属组
        return groupMapper.groups(userID);
    }
    public List<Map<String,Object>> searchGroups(String input){//检索群聊
        return groupMapper.searchGroups(input);
    }
    public Boolean createGroups(Crowds crowds){//创建群聊
        crowds.setGroupId(countGroups()+1);
        if(groupMapper.createGroup(crowds)){
            return groupMapper.insertMember(crowds.getGroupId(),crowds.getMasterId(),true);
        }
        else{
            return false;
        }
    }
    private Integer countGroups(){//查询最大群ID
        return groupMapper.countCrowds();
    }

    public Boolean dismissCrowd(Integer groupId){//解散群
        return groupMapper.dismissCrowd(groupId);
    }
    public Boolean leaveGroup(Integer groupId,Integer userId){//退群
        return groupMapper.leaveGroup(groupId,userId);
    }
}
