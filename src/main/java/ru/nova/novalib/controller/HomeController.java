package ru.nova.novalib.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.nova.novalib.domain.BookPage;
import ru.nova.novalib.service.BookService;

@Controller
@RequestMapping("/")
public class HomeController {

    private BookService bookService;

    public HomeController(BookService bookService) {
        this.bookService = bookService;
    }

    @ModelAttribute
    public void addGenresToModel(Model model){
        model.addAttribute("bookList", bookService.findAll());
    }

    @GetMapping
    public String index(Model model){
        BookPage bookPage = new BookPage();
        bookPage.setPageSize(6);
        model.addAttribute("bookList6", bookService.getBooks(bookPage));
        return "index";
    }
}
