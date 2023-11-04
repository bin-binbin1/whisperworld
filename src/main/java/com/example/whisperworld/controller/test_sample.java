package com.example.whisperworld.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class test_sample {

    private UserData userData = new UserData(); // UserData类用于保存用户数据

    // API endpoint to get user data
    @GetMapping("/{id}")
    public UserData getUser(@PathVariable int id) {
        if (id == userData.getUser_id()) {
            return userData;
        } else {
            throw new UserNotFoundException("User not found");
        }
    }

    // API endpoint to update user data
    @PutMapping("/{id}")
    public UserData updateUser(@PathVariable int id, @RequestBody UserData updatedUserData) {
        if (id == userData.getUser_id()) {
            userData.updateUserData(updatedUserData);
            return userData;
        } else {
            throw new UserNotFoundException("User not found");
        }
    }
}
