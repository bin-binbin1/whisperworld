package com.example.whisperworld.entity;

import lombok.Data;

import java.util.Date;

@Data
public class PrivateMessage {
    private int userId;
    private int receiverId;
    private String messageContent;
    private int messageContentId;
    private Date sendTime;
    private boolean receiveState;
}
