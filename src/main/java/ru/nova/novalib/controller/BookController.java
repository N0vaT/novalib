package ru.nova.novalib.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.nova.novalib.domain.Book;
import ru.nova.novalib.service.BookService;

@Controller()
@RequestMapping("/books/save")
public class BookController {
    private BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public String pageSave(){
        return "save-book";
    }

    @PostMapping
    public String save(@RequestBody Book book){
        bookService.save(book);
        return "add-book";
    }


}
