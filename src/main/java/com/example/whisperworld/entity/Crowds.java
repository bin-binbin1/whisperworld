package com.example.whisperworld.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Crowds {
    private int groupId;
    private int masterId;
    private int managerId;
    private String groupName;
    private Date createDate;
    private int groupNum;
    private String groupBackground;
}
