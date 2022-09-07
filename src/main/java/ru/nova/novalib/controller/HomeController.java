package ru.nova.novalib.controller;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.nova.novalib.domain.BookPage;
import ru.nova.novalib.service.BookService;

@Controller
@RequestMapping("/")
public class HomeController {

    private final BookService bookService;

    public HomeController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public String index(Model model){
        BookPage b1 = new BookPage();
        b1.setSortDirection(Sort.Direction.DESC);
        b1.setSortBy("rating");
        model.addAttribute("ratingList", bookService.getPage(1, 6, b1));
        BookPage b2 = new BookPage();
        b2.setSortDirection(Sort.Direction.DESC);
        model.addAttribute("dateList", bookService.getPage(1, 6, b2));
        return "index";
    }
}
