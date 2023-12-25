package com.example.whisperworld.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.whisperworld.service.novelService;

import java.io.IOException;

@RestController
public class novelController {
    novelService service;
    @Autowired
    public novelController(novelService service){
        this.service=service;
    }

    @GetMapping("/novel/{id}/content")
    public String getNovelContentById(@PathVariable int id, Model model) throws IOException {
        String content = service.getNovelContentById(id);
        model.addAttribute("content", content);
        return "novelDetail";
    }

    @GetMapping("/novel/{id}/nextPage")
    public String getNextPage(@PathVariable int id, Model model) throws IOException {
        String content = service.getNextPage(id);
        model.addAttribute("content", content);
        return "novelDetail";
    }

    @GetMapping("/novel/{id}/nextChapter")
    public String getNextChapter(@PathVariable int id, Model model) throws IOException {
        String content = service.getNextChapter(id);
        model.addAttribute("content", content);
        return "novelDetail";
    }

    @GetMapping("/novel/{id}/jumpPage/{pageNumber}")
    public String jumpToPage(@PathVariable int id, @PathVariable int pageNumber, Model model) throws IOException {
        String content = service.jumpToPage(id, pageNumber);
        model.addAttribute("content", content);
        return "novelDetail";
    }

    @PostMapping("/novel/{id}/addBookmark")
    public String addBookmark(@PathVariable int id, @RequestParam int pageNumber, Model model) {
        String message = service.addBookmark(id, pageNumber);
        model.addAttribute("message", message);
        return "bookmarkAdded";
    }

    @GetMapping("/novel/{id}/download")
    public String downloadNovel(@PathVariable int id, Model model) {
        String downloadLink = service.downloadNovel(id);
        model.addAttribute("downloadLink", downloadLink);
        return "downloadNovel";
    }

    @GetMapping("/novel/{id}/close")
    public String closeNovel(@PathVariable int id) {
        return service.closeNovel(id);
    }

}
