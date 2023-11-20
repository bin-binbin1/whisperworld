package com.example.whisperworld.specialClasses;

import lombok.Data;

import java.util.Date;

@Data
public class Comments {
    private String topicCommentusername;
    private String commentContent;
    private Date commentTime;
    private Integer commentId;
}
