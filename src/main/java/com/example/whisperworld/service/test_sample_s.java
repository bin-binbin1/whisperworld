package com.example.whisperworld.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.whisperworld.mapper.test_sample_m;
import com.example.whisperworld.entity.User;

@Service
public class test_sample_s {
    private final test_sample_m userMapper;

    @Autowired
    public test_sample_s(test_sample_m userMapper) {
        this.userMapper = userMapper;
    }

    public User getUser(Integer id) {
        return userMapper.getUserById(id);
    }

}
