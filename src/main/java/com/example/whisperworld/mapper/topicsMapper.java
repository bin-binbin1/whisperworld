package com.example.whisperworld.mapper;

import com.example.whisperworld.entity.TopicReplies;
import com.example.whisperworld.entity.Topics;
import com.example.whisperworld.specialClasses.Comments;
import com.example.whisperworld.specialClasses.topics;
import org.apache.ibatis.annotations.*;
import org.springframework.data.redis.listener.Topic;

import java.util.Date;
import java.util.List;

@Mapper
public interface topicsMapper {
    @Select("select userName,TopicLaunchTime,TopicContent,TopicID,LikeNum from topics JOIN users ON users.userID = topics.UserID " +
            "ORDER BY LikeNum DESC, TopicLaunchTime DESC " +
            "LIMIT #{start},#{length}")
    List<topics> topics(int start,int length);//查询所有话题信息并按照发布时间和点赞数降序排序
    @Select("SELECT MAX(TopicID) FROM topics ")
    Integer countTopics();//查找话题数目
    @Results({
            @Result(property = "topicCommentusername", column = "userName"),
            @Result(property = "commentContent", column = "CommentContent"),
            @Result(property = "commentTime", column = "CommentTime"),
            @Result(property = "commentId", column = "commentId")
    })
    @Select("SELECT userName,CommentContent,CommentTime,CommentID FROM topic_replies " +
            "JOIN users on topic_replies.CommentUserID = userID WHERE TopicID=#{topicId} ORDER BY CommentTime ASC")
    List<Comments> topicReplies(Integer topicId);//查找话题的所有评论并按照发表时间降序排序

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
    @Select("select userName,TopicLaunchTime,TopicContent,TopicID,LikeNum from topics JOIN users ON users.userID = topics.UserID " +
            "ORDER BY TopicLaunchTime DESC " +
            "LIMIT #{start},#{length}")
    List<topics> topicsByLatest(int start, int length);
}
