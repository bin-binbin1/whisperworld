package com.example.whisperworld.specialClasses;

import lombok.Data;

import java.util.Date;

@Data
public class historyMsg {
    String content;
    String userName;
    Date time;
    Boolean self;
}
