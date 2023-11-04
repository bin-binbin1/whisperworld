package com.example.whisperworld.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class AuditLog {
    private int logId;
    private String tableName;
    private String action;
    private Timestamp actionTime;
}
