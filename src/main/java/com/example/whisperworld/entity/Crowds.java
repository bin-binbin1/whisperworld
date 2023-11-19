package com.example.whisperworld.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Crowds {
    private int groupId;
    private int masterId;
    private int managerId;
    private String groupName;
    private Date createDate;
    private int groupNum;
    private String groupBackground;
}
