package ru.nova.novalib.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.nova.novalib.domain.Book;
import ru.nova.novalib.service.BookService;
import ru.nova.novalib.service.GenreService;

@Slf4j
@Controller()
@RequestMapping("/admin/books/save")
@SessionAttributes("book")
public class BookController {
    private BookService bookService;
    private GenreService genreService;

    @Autowired
    public BookController(BookService bookService, GenreService genreService) {
        this.bookService = bookService;
        this.genreService = genreService;
    }

    @ModelAttribute
    public void addGenreToModel(Model model){
            model.addAttribute("genres", genreService.findAll());
    }

    @GetMapping
    public String pageSave(){
        return "save-book";
    }

//    @PostMapping
//    public String save(@RequestBody Book book){
//        bookService.save(book);
//        return "admin";
//    }

    @PostMapping("/genre")
    public String addGenre(){

        return "redirect:/admin/books/save";
    }


}
