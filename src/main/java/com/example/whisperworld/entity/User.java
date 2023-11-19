package com.example.whisperworld.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User{
    private Integer userID;
    private String userName;
    private String userPassword;
    private String userSex;
    private Date urDate;
    private String userPhone;
    private boolean userState;
    private Integer userLevel;
    private Date userBDate;
}