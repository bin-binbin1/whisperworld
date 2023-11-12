package com.example.whisperworld.mapper;

import com.example.whisperworld.entity.TopicReplies;
import com.example.whisperworld.entity.Topics;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.List;

@Mapper
public interface topicsMapper {
    @Select("SELECT topicID FROM topics WHERE userID=#{userid}")
    int topicID(int userid);//查找用户的话题号

    @Select("SELECT * FROM topics ORDER BY TopicLaunchTime DESC, LikeNum DESC ")
    List<Topics> topics();//查找所有话题并按照发布时间和点赞数降序排序

    @Select("SELECT count(*) FROM topics ")
    int countTopics();//查找话题数目

    @Select("SELECT * FROM topics_replies ORDER BY commentTime WHERE topicId=#{topicId}")
    List<TopicReplies> topicReplies(Topics topics);//查找话题的所有评论并按照发表时间降序排序

    @Insert("INSERT INTO topics VALUES(#{topicId},#{userId},#{topicCommentNum},#{topicLaunchTime},#{TopicContent},#{likeNum})")
    int postTopic(Topics topic);//发布话题

    @Insert("INSERT INTO topics_replies VALUES(#{topicId},#{commentUserId},#{commentId},#{commentTime},#{commentContent},#{commentNum})")
    int postTopicReplies(TopicReplies topicReplies);//发表评论

    @Select("SELECT count(*) FROM topics_replies WHERE topicId=#{topicId} ")
    int topicRepliesNum(TopicReplies topicReplies);//获取话题的评论个数

    @Update("UPDATE topics SET LikeNum=LikeNum+1 WHERE userId=#{userid} AND TopicID=#{topicid}")
    boolean addLikeNum(Topics topic);//点赞+1

    @Select("SELECT likeNum from topics WHERE TopicID=#{topicid}")
    int updateLikeNum(Topics topic);//获取点赞数

}
