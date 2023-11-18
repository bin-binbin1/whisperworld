package com.example.whisperworld.entity;

import lombok.Data;

import java.util.Date;

@Data
public class PrivateMessage {
    private Integer userId;
    private Integer receiverId;
    private String messageContent;
    private Integer messageContentId;
    private Date sendTime;
    private boolean receiveState;
}
