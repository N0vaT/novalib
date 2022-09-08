package ru.nova.novalib.controller;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.nova.novalib.domain.BookPage;
import ru.nova.novalib.service.BookService;
import ru.nova.novalib.service.GenreService;

@Controller
@RequestMapping("/catalog/byGenre")
@SessionAttributes({"bookPage", "books"})
public class BookByGenreController {

    private BookService bookService;
    private GenreService genreService;

    public BookByGenreController(BookService bookService, GenreService genreService) {
        this.bookService = bookService;
        this.genreService = genreService;
    }

    @ModelAttribute
    public void addGenresToModel(Model model){
        model.addAttribute("genres", genreService.findAll());
    }

    @GetMapping()
    public String getByGenre(@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                             @RequestParam(value = "size", required = false, defaultValue = "3") int size,
                             BookPage bookPage, Model model){
        bookPage.setSortDirection(Sort.Direction.DESC);
        model.addAttribute("genres", genreService.findAll());
        model.addAttribute("books", bookService.getByGenreId(pageNumber, size, bookPage));
        return "bookByGenre";
    }

    @PostMapping()
    public String getByGenrePost(@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                             @RequestParam(value = "size", required = false, defaultValue = "3") int size,
                             BookPage bookPage, Model model){
        bookPage.setSortBy("rating");
        bookPage.setSortDirection(Sort.Direction.DESC);
        model.addAttribute("books", bookService.getByGenreId(pageNumber, size, bookPage));
        return "bookByGenre";
    }

    @PostMapping("/revers")
    public String revers(@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                         @RequestParam(value = "size", required = false, defaultValue = "3") int size,
                         BookPage bookPage, Model model){
        Sort.Direction sortDirection = bookPage.getSortDirection();
        bookPage.setSortDirection(
                sortDirection==Sort.Direction.DESC?
                        Sort.Direction.ASC : Sort.Direction.DESC);
        model.addAttribute("books", bookService.getByGenreId(pageNumber, size, bookPage));
        return "bookByGenre";
    }
}
