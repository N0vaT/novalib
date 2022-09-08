package ru.nova.novalib.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import ru.nova.novalib.domain.BookPage;
import ru.nova.novalib.service.BookService;
import ru.nova.novalib.service.GenreService;

@Controller
@RequestMapping("/catalog")
@SessionAttributes({"bookPage", "books"})
public class BookCatalogController {

    private final BookService bookService;
    private final GenreService genreService;

    public BookCatalogController(BookService bookService, GenreService genreService) {
        this.bookService = bookService;
        this.genreService = genreService;
    }

    @ModelAttribute
    public void addGenresToModel(Model model){
        model.addAttribute("genres", genreService.findAll());
    }

    @GetMapping()
    public String getCatalog(@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                             @RequestParam(value = "size", required = false, defaultValue = "3") int size,
                             BookPage bookPage, Model model, SessionStatus sessionStatus){
        if(bookPage==null) bookPage = new BookPage();
        model.addAttribute("bookPage", bookPage);
        model.addAttribute("books", bookService.getPage(pageNumber, size, bookPage));
        return "bookCatalog";
    }


    @GetMapping("/rating")
    public String getCatalogRating(@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                                   @RequestParam(value = "size", required = false, defaultValue = "3") int size,
                                   BookPage bookPage, Model model){
        bookPage.setSortBy("rating");
        bookPage.setSortDirection(Sort.Direction.DESC);
        model.addAttribute("books", bookService.getPage(pageNumber, size, bookPage));
        return "redirect:/catalog";
    }
    @GetMapping("/title")
    public String getCatalogTitle(@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                                  @RequestParam(value = "size", required = false, defaultValue = "3") int size,
                                  BookPage bookPage, Model model){
        bookPage.setSortBy("title");
        bookPage.setSortDirection(Sort.Direction.ASC);
        model.addAttribute("books", bookService.getPage(pageNumber, size, bookPage));
        return "redirect:/catalog";
    }
    @GetMapping("/year")
    public String getCatalogYearPublished(@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                                          @RequestParam(value = "size", required = false, defaultValue = "3") int size,
                                          BookPage bookPage, Model model){
        bookPage.setSortBy("yearPublished");
        bookPage.setSortDirection(Sort.Direction.ASC);
        model.addAttribute("books", bookService.getPage(pageNumber, size, bookPage));
        return "redirect:/catalog";
    }
    @GetMapping("/date")
    public String getCatalogDate(@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                                          @RequestParam(value = "size", required = false, defaultValue = "3") int size,
                                          BookPage bookPage, Model model){
        bookPage.setSortBy("id");
        bookPage.setSortDirection(Sort.Direction.ASC);
        model.addAttribute("books", bookService.getPage(pageNumber, size, bookPage));
        return "redirect:/catalog";
    }

    @PostMapping("/revers")
    public String revers(@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                                 @RequestParam(value = "size", required = false, defaultValue = "3") int size,
                                 BookPage bookPage, Model model){
        Sort.Direction sortDirection = bookPage.getSortDirection();
        bookPage.setSortDirection(
                sortDirection==Sort.Direction.DESC?
                        Sort.Direction.ASC : Sort.Direction.DESC);
        model.addAttribute("books", bookService.getPage(pageNumber, size, bookPage));
        return "bookCatalog";
    }

    @GetMapping("/search")
    public String getSearch(@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                                 @RequestParam(value = "size", required = false, defaultValue = "3") int size,
                                 BookPage bookPage, Model model, String keyword){
        if(keyword!=null) {
            bookPage.setSearch(keyword);
            bookPage.setSortBy("title");
            bookPage.setSortDirection(Sort.Direction.ASC);
            model.addAttribute("bookPage", bookPage);
            model.addAttribute("books", bookService.getByKeyword(pageNumber, size, bookPage));
        }else {
            bookPage.setSortBy("title");
            bookPage.setSortDirection(Sort.Direction.ASC);
            model.addAttribute("bookPage", bookPage);
            model.addAttribute("books", bookService.getPage(pageNumber, size, bookPage));
        }
        return "bookCatalog";
    }

}
