package com.estate.utils;


import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@Component
public class FileUtils {

    private String baseUrl ;

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public void uploadFile(MultipartFile file, String location , HttpServletRequest request) throws IOException {
        String fileName =  file.getOriginalFilename();
        Path path = Paths.get(location);
        Files.createDirectories(path);
        File saveFile = Paths.get(location, fileName).toFile();
        file.transferTo(saveFile);
        this.setBaseUrl(ServletUriComponentsBuilder.fromRequestUri(request)
                .replacePath(null)
                .build()
                .toUriString());

    }


}
