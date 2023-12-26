package com.example.whisperworld.entity;

import lombok.Data;

@Data
public class Novel {
    private int id;
    private String title;
    private String author;
    private String description;
    private int chapter;
}
