package ru.nova.novalib.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.nova.novalib.domain.dto.UserDto;
import ru.nova.novalib.service.BookService;
import ru.nova.novalib.service.FileUploadService;
import ru.nova.novalib.service.UserService;

import java.security.Principal;

@Controller()
@RequestMapping("/admin")
@SessionAttributes("book")
public class AdminController {

    private final FileUploadService fileUploadService;
    private final BookService bookService;
    private final UserService userService;

    public AdminController(FileUploadService fileUploadService, BookService bookService, UserService userService) {
        this.fileUploadService = fileUploadService;
        this.bookService = bookService;
        this.userService = userService;
    }

    @ModelAttribute
    public void addGenresToModel(Model model, Principal principal){
        model.addAttribute("bookList", bookService.findAll());
        model.addAttribute("userDto", new UserDto());
        if(principal!=null) {
            model.addAttribute("user", userService.findByLogin(principal.getName()));
        }
    }

    @GetMapping
    public String index(Model model){
        return "admin";
    }

    @PostMapping
    public String addBook(@RequestParam("file")MultipartFile file, Model model){
        model.addAttribute("book", fileUploadService.saveFile(file));
        return "redirect:/admin/books/save";
    }

}
