package com.example.whisperworld.specialClasses;

import lombok.Data;

import java.util.List;

@Data
public class topics {
    String username;
    String topicLaunchTime;
    String topicContent;
    Integer topicId;
    Integer likeNum;
    List<Comments> comments;
}
