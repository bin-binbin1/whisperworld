package com.example.whisperworld.specialClasses;

import lombok.Data;

import java.util.Date;

@Data
public class oneTopic {
    private Integer topicID;
    private String userName;
    private Date topicLaunchTime;
    private String topicContent;
}
