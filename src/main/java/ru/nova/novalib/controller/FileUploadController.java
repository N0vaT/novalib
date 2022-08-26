package ru.nova.novalib.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.nova.novalib.service.BookService;
import ru.nova.novalib.service.FileUploadService;

@Controller()
@RequestMapping("/admin")
@SessionAttributes("book")
public class FileUploadController {

    private FileUploadService fileUploadService;
    private BookService bookService;

    public FileUploadController(FileUploadService fileUploadService, BookService bookService) {
        this.fileUploadService = fileUploadService;
        this.bookService = bookService;
    }

    @ModelAttribute
    public void addGenresToModel(Model model){
        model.addAttribute("bookList", bookService.findAll());
    }

    @GetMapping
    public String index(){
        return "admin";
    }

    @PostMapping
    public String addBook(@RequestParam("file")MultipartFile file, Model model){
        model.addAttribute("book", fileUploadService.saveFile(file));
        return "redirect:/admin/books/save";
    }

}