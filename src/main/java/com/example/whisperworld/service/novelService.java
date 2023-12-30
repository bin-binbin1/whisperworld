package com.example.whisperworld.service;

import com.example.whisperworld.entity.Novel;
import com.example.whisperworld.entity.bookmark;
import com.example.whisperworld.entity.bookmarks;
import com.example.whisperworld.mapper.novelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class novelService {
    @Autowired
    novelMapper mapper;
    private int Lines=20;
    private static final String NOVELS_DIRECTORY = "E:/新建文件夹"; //"/home/fun/novels";
    public List<Novel> getNovels() {
        return mapper.getAllNovels();
    }

    public Map<String, Object> getContent(int id, int chapter, int page) throws IOException {
        String dir=NOVELS_DIRECTORY+'/'+mapper.getTitleByID(id);
        Path novelPath = Paths.get(dir, chapter+".txt");

        List<String> lines = Files.readAllLines(novelPath);
        int totalLines = lines.size();
        int startLine =(page-1)*Lines;
        int endLine = Math.min(startLine+20, totalLines);

        Map<String,Object> m=new HashMap<>();
        if(startLine<endLine) {
            List<String> selectedLines = lines.subList(startLine, endLine);
            m.put("content", String.join("\n", selectedLines));
        }else{
            m.put("content","没有此页!");
        }
            m.put("isEnd", endLine==totalLines);
        return m;
    }

    public List<bookmark> getBookmark(String userId) {
        return mapper.getBookmarkByID(Integer.parseInt(userId));
    }

    public void addmark(bookmarks marks) {
        mapper.addmark(marks);
    }
    public Resource downloadNovelByTitle(int id) throws IOException {
        Path filePath = Paths.get(NOVELS_DIRECTORY, mapper.getTitleByID(id) + ".txt");
        return new FileSystemResource(filePath.toFile());
    }
}
