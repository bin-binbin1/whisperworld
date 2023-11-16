package com.example.whisperworld.mapper;

import com.example.whisperworld.entity.TopicReplies;
import com.example.whisperworld.entity.Topics;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.data.redis.listener.Topic;

import java.util.Date;
import java.util.List;

@Mapper
public interface topicsMapper {
    @Select("SELECT userName FROM users WHERE UserID=#{userid}")
    String topicName(int userid);//查找话题用户

    @Select("SELECT * FROM topics ORDER BY LikeNum DESC, TopicLaunchTime DESC ")
    List<Topics> topics();//查找所有话题并按照发布时间和点赞数降序排序

    @Select("SELECT MAX(TopicID) FROM topics ")
    int countTopics();//查找话题数目

    @Select("SELECT * FROM topic_replies WHERE TopicID=#{topicId} ORDER BY CommentTime ")
    List<TopicReplies> topicReplies(Topics topics);//查找话题的所有评论并按照发表时间降序排序

    @Insert("INSERT INTO topics VALUES(#{topicId},#{userId},#{topicCommentNum},#{topicLaunchTime},#{topicContent},#{likeNum})")
    void postTopic(Topics topic);//发布话题

    @Insert("INSERT INTO topic_replies VALUES(#{topicId},#{commentUserId},#{commentContent},#{commentId},#{commentTime})")
    void postTopicReplies(TopicReplies topicReplies);//发表评论

    @Update("UPDATE topics SET LikeNum=LikeNum+1 WHERE TopicID=#{topicId}")
    void addLikeNum(int topicId);//点赞+1

    @Select("SELECT likeNum from topics WHERE TopicID=#{topicId}")
    int updateLikeNum(Topics topic);//获取点赞数

    @Select("SELECT TopicCommentNum FROM topics WHERE topicID=#{topicId}")
    int getCommentNumByID(Integer topicId);

    @Select("SELECT userName FROM users WHERE userID=#{userId}")
    String getNameByID(Integer userId);

    @Select("SELECT EXISTS(SELECT * FROM likes WHERE TopicID=#{topicId} AND LikeUserID=#{userId})")
    boolean isDumplicateLike(Integer topicId, Integer userId);

    @Insert("INSERT likes VALUES(#{topicId},#{likeuserid})")
    void addlikeUser(Integer topicId, Integer likeuserid);//添加点赞用户
}
