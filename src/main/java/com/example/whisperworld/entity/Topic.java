package com.example.whisperworld.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "topics")
public class Topic {

    @Id
    @Column(name = "TopicID")
    private int topicId;

    @Column(name = "TopicCommentNum")
    private int topicCommentNum;

    @Column(name = "TopicLaunchTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date topicLaunchTime;

    @Column(name = "TopicContent")
    private String topicContent;

    @Column(name = "LikeNum")
    private int likeNum;

    @ManyToOne
    @JoinColumn(name = "UserID", referencedColumnName = "UserID")
    private User user;

    public Topic() {
    }

    public Topic(int topicId, int topicCommentNum, Date topicLaunchTime, String topicContent, int likeNum, User user) {
        this.topicId = topicId;
        this.topicCommentNum = topicCommentNum;
        this.topicLaunchTime = topicLaunchTime;
        this.topicContent = topicContent;
        this.likeNum = likeNum;
        this.user = user;
    }

    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    public int getTopicCommentNum() {
        return topicCommentNum;
    }

    public void setTopicCommentNum(int topicCommentNum) {
        this.topicCommentNum = topicCommentNum;
    }

    public Date getTopicLaunchTime() {
        return topicLaunchTime;
    }

    public void setTopicLaunchTime(Date topicLaunchTime) {
        this.topicLaunchTime = topicLaunchTime;
    }

    public String getTopicContent() {
        return topicContent;
    }

    public void setTopicContent(String topicContent) {
        this.topicContent = topicContent;
    }

    public int getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(int likeNum) {
        this.likeNum = likeNum;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
