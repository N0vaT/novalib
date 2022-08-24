package ru.nova.novalib.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import ru.nova.novalib.domain.Author;
import ru.nova.novalib.domain.Book;
import ru.nova.novalib.domain.Genre;
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
    public void addGenresToModel(Model model){
            model.addAttribute("genreList", genreService.findAll());
    }

    @ModelAttribute
    public void addGenreToModel(Model model){
            model.addAttribute("genreForm", new Genre());
    }
    @ModelAttribute
    public void addAuthorToModel(Model model){
        model.addAttribute("authorForm", new Author());
    }

    @GetMapping
    public String pageSave(){
        return "save-book";
    }

    @PostMapping
    public String save(Book book, Author author,  SessionStatus sessionStatus){
        book.addAuthor(author);
        bookService.save(book);
        log.info("Order submitted: {}", book);
        sessionStatus.setComplete();
        return "admin";
    }

    @PostMapping("/genre")
    public String addGenre( Genre genre, @ModelAttribute Book book){
        System.out.println("");
        book.addGenre(genreService.findById(genre.getGenreId()));
        System.out.println("");
        return "redirect:/admin/books/save";
    }


}
