package ru.nova.novalib.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.nova.novalib.service.FileUploadService;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller("/")
public class FileUploadController {

    FileUploadService fileUploadService;

    @Autowired
    public FileUploadController(FileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }

    @GetMapping
    public String index(){
        return "index";
    }

    @PostMapping
    public String addBook(@RequestParam("file")MultipartFile file){
        fileUploadService.saveFile(file);
        return "redirect:/";
    }

}
