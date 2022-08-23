package ru.nova.novalib.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.nova.novalib.service.FileUploadService;

@Controller()
@RequestMapping("/")
public class FileUploadController {

    FileUploadService fileUploadService;

    @Autowired
    public FileUploadController(FileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }

    @GetMapping
    public String index(){
        return "add-book";
    }

    @PostMapping
    public String addBook(@RequestParam("file")MultipartFile file, Model model){
        model.addAttribute("book", fileUploadService.saveFile(file));
        return "redirect:/books/save";
    }

}
