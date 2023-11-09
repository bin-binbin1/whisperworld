package com.example.whisperworld.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Messages { //非数据库，但是用来发送信息
    private String content;
    private boolean self;
    private Date sendTimem;
    private boolean receiveState;
}
