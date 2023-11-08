package com.example.whisperworld.entity;

import lombok.Data;
import java.util.Date;
@Data
public class TopicReplies {
    private int topicId;
    private int commentUserId;
    private int commentId;
    private Date commentTime;
    private String commentContent;
    private int commentNum;
}
