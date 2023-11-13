package com.example.whisperworld.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.whisperworld.mapper.profileMapper;
import com.example.whisperworld.entity.User;

import java.util.Calendar;
import java.util.Date;

@Service
public class profileService {
    private final profileMapper userMapper;

    @Autowired
    public profileService(profileMapper userMapper) {
        this.userMapper = userMapper;
    }

    public User getUser(Integer id) {
        return userMapper.getUserById(id);
    }

    public int yearsBetween(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);

        return year1 - year2;
    }

}
