package com.example.whisperworld.entity;

import lombok.Data;

@Data
public class Friends {
    private Integer userId;
    private Integer friendId;
    private boolean state;
}
