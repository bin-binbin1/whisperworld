package com.example.whisperworld.entity;

import lombok.Data;

import java.util.Date;

@Data
public class CrowdsMessage {
    private int groupId;
    private int groupMessageId;
    private String conversationContent;
    private int userId;
    private Date sendTime;
}
