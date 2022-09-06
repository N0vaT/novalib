package ru.nova.novalib.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.nova.novalib.service.BookService;

@Controller
@RequestMapping("/catalog")
public class BookCatalogController {

    private final BookService bookService;

    @Autowired
    public BookCatalogController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping()
    public String getCatalog(@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                             @RequestParam(value = "size", required = false, defaultValue = "18") int size, Model model){
        model.addAttribute("books", bookService.getPage(pageNumber, size, "id", Sort.Direction.ASC));
        return "bookCatalog";
    }
}
