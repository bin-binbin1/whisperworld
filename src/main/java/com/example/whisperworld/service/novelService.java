package com.example.whisperworld.service;

import com.example.whisperworld.entity.Novel;
import com.example.whisperworld.entity.bookmark;
import com.example.whisperworld.entity.bookmarks;
import com.example.whisperworld.mapper.novelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;

@Service
public class novelService {
    @Autowired
    novelMapper mapper;
    private int Lines=20;
    private static final String NOVELS_DIRECTORY = "/home/fun/novels";
    public List<Novel> getNovels() {
        return mapper.getAllNovels();
    }

    public String getContent(int id, int chapter, int page) throws IOException {
        String dir=NOVELS_DIRECTORY+'/'+id+'/'+chapter;
        Path novelPath = Paths.get(dir, "1.txt");

        List<String> lines = Files.readAllLines(novelPath);
        int totalLines = lines.size();
        int startLine =(page-1)*Lines;
        int endLine = Math.min(startLine+20, totalLines);
        List<String> selectedLines = lines.subList(startLine, endLine);

        return String.join("\n", selectedLines);
    }

    public List<bookmark> getBookmark(String userId) {
        return mapper.getBookmarkByID(Integer.parseInt(userId));
    }

    public void addmark(bookmarks marks) {
        mapper.addmark(marks);
    }
}
