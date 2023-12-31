package com.example.whisperworld.service;

import com.example.whisperworld.entity.TopicReplies;
import com.example.whisperworld.entity.Topics;
import com.example.whisperworld.mapper.topicsMapper;
import com.example.whisperworld.specialClasses.Comments;
import com.example.whisperworld.specialClasses.topics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class topicService {
    private final topicsMapper topicsMapper;

    @Autowired
    public topicService(topicsMapper topicsMapper){
        this.topicsMapper = topicsMapper;
    }

    public List<topics> showTopics(int start,int length){//获取所有话题
        List<topics> tp=topicsMapper.topics(start,length);
        for(topics t:tp){
            t.setComments(showTopicReplies(t.getTopicId()));
        }
        Collections.reverse(tp);
        return tp;
    }
    public List<Comments> showTopicReplies(Integer topicId){//获取话题所有评论
        return topicsMapper.topicReplies(topicId);
    }
    public boolean postTopic(Topics topics){//发布话题
        topics.setTopicLaunchTime(new Date());
        topics.setLikeNum(0);
        topics.setTopicCommentNum(0);
        return postTopicId(topics);
    }
    private synchronized boolean postTopicId(Topics topics){//生成话题号
        if(topicsMapper.countTopics() == null)
        {
            topics.setTopicId(0);
        }
        else{
            topics.setTopicId(topicsMapper.countTopics()+1);
        }
        topicsMapper.postTopic(topics);
        //发送失败判断
        return true;
    }

    public boolean postComment(TopicReplies topicreplie){//发布评论
        topicreplie.setCommentTime(new Date());
        return postCommentId(topicreplie);
    }
    private synchronized boolean postCommentId(TopicReplies topicreplie){//生成评论号

        topicreplie.setCommentId(topicsMapper.getCommentNumByID(topicreplie.getTopicId()));
        topicsMapper.postTopicReplies(topicreplie);
        //判断失败
        return true;
    }

    public int likeTopic(Topics topic, Integer loginId){//更新点赞
        if(topicsMapper.isDumplicateLike(topic.getTopicId(), loginId) == false){//如果没有点过赞
            topicsMapper.addLikeNum(topic.getTopicId());//增加点赞数
            topicsMapper.addlikeUser(topic.getTopicId(),loginId);//增加点赞用户
        }
        return topicsMapper.updateLikeNum(topic);
    }

    public String getNameByID(Integer userId){
        return topicsMapper.getNameByID(userId);
    }

    public List<topics> showTopicsByLatest(int start, int length) {
        List<topics> tp=topicsMapper.topicsByLatest(start,length);
        for(topics t:tp){
            t.setComments(showTopicReplies(t.getTopicId()));
        }
        Collections.reverse(tp);
        return tp;
    }
}
