package com.example.whisperworld.service;

// UserRepository.java
import java.util.List;


public interface test_sample_s {
    User findById(Long id);
    List<User> findAll();
    void save(User user);
    void update(User user);
    void delete(Long id);
}
