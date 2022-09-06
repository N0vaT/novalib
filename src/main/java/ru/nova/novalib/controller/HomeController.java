package ru.nova.novalib.controller;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
        model.addAttribute("ratingList", bookService.getPage(1, 6, "rating", Sort.Direction.DESC));
        model.addAttribute("dateList", bookService.getPage(1, 6, "id", Sort.Direction.DESC));
        return "index";
    }
}
