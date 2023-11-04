package com.example.whisperworld.controller;

import com.example.whisperworld.entity.User;
import com.example.whisperworld.mapper.fwj_mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class fwj {


    private final fwj_mapper userRepository;

    @Autowired
    public fwj(fwj_mapper entityRepository) {
        this.userRepository = entityRepository;
    }

    @GetMapping("/testmysql")
    public String testmysql(Model model){
        List<User> entities = userRepository.findAll();

        // 将数据输出到浏览器
        StringBuilder response = new StringBuilder();
        for (User entity : entities) {
            System.out.println(entity.toString());
        }

        return "home";
    }
}
