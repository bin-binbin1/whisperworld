package com.example.whisperworld.specialClasses;

import lombok.Data;

import java.util.Date;

@Data
public class historyMsg {
    Integer GroupID;
    String content;
    String userName;
    Date time;
    Boolean self;
}
