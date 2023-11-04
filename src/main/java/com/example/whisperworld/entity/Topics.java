package com.example.whisperworld.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Topics {
    private int topicId;
    private int userId;
    private int topicCommentNum;
    private Date topicLaunchTime;
    private String topicContent;
    private int likeNum;
}
