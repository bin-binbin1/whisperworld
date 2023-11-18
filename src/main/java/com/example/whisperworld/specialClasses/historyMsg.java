package com.example.whisperworld.specialClasses;

import lombok.Data;

@Data
public class historyMsg {
    String content;
    String username;
    String time;
    Integer userID;
    Boolean self;
}
