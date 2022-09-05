package ru.nova.novalib.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.nova.novalib.service.BookService;

@Controller
@RequestMapping("/")
public class HomeController {

    private BookService bookService;

    public HomeController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public String index(Model model){
        model.addAttribute("bookList6", bookService.getPage(1, 6));
        return "index";
    }
}
