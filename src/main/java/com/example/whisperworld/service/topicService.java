package com.example.whisperworld.service;

import com.example.whisperworld.entity.TopicReplies;
import com.example.whisperworld.entity.Topics;
import com.example.whisperworld.mapper.topicsMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.listener.Topic;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class topicService {
    private int topicid;
    private final topicsMapper topicsMapper;

    @Autowired
    public topicService(topicsMapper topicsMapper){
        this.topicsMapper = topicsMapper;
        topicid = topicsMapper.countTopics();
    }

    public List<Topics> showTopics(){//获取所有话题

        return topicsMapper.topics();
    }
    public List<TopicReplies> showTopicReplies(Topics topics){//获取话题所有评论

        return topicsMapper.topicReplies(topics);
    }
    public boolean postTopic(Topics topics){//发布话题
        topics.setTopicLaunchTime(new Date());
        topics.setLikeNum(0);
        topics.setTopicCommentNum(0);
        if(postTopic_(topics)){
            return true;
        }
        else{
            return false;
        }
    }
    private synchronized boolean postTopic_(Topics topics){
        topics.setTopicId(topicid);
        topicid++;
        //发送失败判断
        return true;
    }

    public int likeTopic(Topics topic){//更新点赞
        return topicsMapper.updateLikeNum(topic);
    }

}
