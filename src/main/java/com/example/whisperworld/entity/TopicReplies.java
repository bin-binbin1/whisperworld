package com.example.whisperworld.entity;

import lombok.Data;

@Data
public class TopicReplies {
    private int topicId;
    private int topicCommentPersonId;
    private String commentContent;
    private int commentNum;
}
