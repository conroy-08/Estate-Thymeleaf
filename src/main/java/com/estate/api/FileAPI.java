package com.estate.api;

import com.estate.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController(value = "fileApiOfAdmin")
@RequestMapping("/api/files")
public class FileAPI {

    @Value("${image.location}")
    private String location;


    private final FileUtils fileUtils;
    @Autowired
    public FileAPI(FileUtils fileUtils) {
        this.fileUtils = fileUtils;
    }


    @PostMapping()
    public void uploadFile(@RequestParam("thumbnail") MultipartFile file, RedirectAttributes redirectAttributes, HttpServletRequest request) throws IOException {
        fileUtils.uploadFile(file, location, request);
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");
    }
}
