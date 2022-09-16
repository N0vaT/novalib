package ru.nova.novalib.controller;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import ru.nova.novalib.domain.BookPage;
import ru.nova.novalib.domain.dto.UserDto;
import ru.nova.novalib.service.BookService;
import ru.nova.novalib.service.UserService;

import java.security.Principal;


@Controller
@RequestMapping("/")
public class HomeController {

    private final BookService bookService;
    private final UserService userService;

    public HomeController(BookService bookService, UserService userService) {
        this.bookService = bookService;
        this.userService = userService;
    }

    @ModelAttribute
    public void addGenresToModel(Model model, Principal principal) {
        model.addAttribute("userDto", new UserDto());
        if(principal!=null) {
            model.addAttribute("user", userService.findByLogin(principal.getName()));
        }
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
