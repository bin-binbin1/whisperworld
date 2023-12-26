package com.example.whisperworld.entity;

import lombok.Data;

@Data
public class bookmarks {
    private int id;
    private int userID;
    private int novel_id;
    private int page_number;
    private int chapter;
}
