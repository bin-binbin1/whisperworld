package com.example.whisperworld.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class CrowdsMessage {
    private int groupId;
    private int groupMessageId;
    private String messageContent;
    private int userId;
    private Date sendTime;
}
