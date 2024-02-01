package com.example.whisperworld.controller;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class gameController {
    private static final String gameDirectory="/home/fun/game";
    @GetMapping("/game/dowload/{id}")
    public ResponseEntity<Resource> downloadGameByID(@PathVariable int id) throws IOException{
        Path filePath= Paths.get(gameDirectory+'/'+id);
        Resource resource = (Resource) new FileSystemResource(filePath.toFile());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", id + ".zip");

        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);
    }

}
