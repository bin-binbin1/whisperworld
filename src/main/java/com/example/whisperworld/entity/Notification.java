package com.example.whisperworld.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Notification {
    private int notificationId;
    private String notificationContent;
    private Date notificationTime;
    private int supervisorId;
}
