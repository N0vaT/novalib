package ru.nova.novalib.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import ru.nova.novalib.domain.BookPage;
import ru.nova.novalib.domain.dto.UserDto;
import ru.nova.novalib.service.BookService;
import ru.nova.novalib.service.GenreService;
import ru.nova.novalib.service.UserService;

import java.security.Principal;

@Controller
@RequestMapping("/catalog")
@SessionAttributes({"bookPage", "books"})
public class BookCatalogController {

    private final BookService bookService;
    private final GenreService genreService;
    private final UserService userService;

    public BookCatalogController(BookService bookService, GenreService genreService, UserService userService) {
        this.bookService = bookService;
        this.genreService = genreService;
        this.userService = userService;
    }

    @ModelAttribute
    public void addGenresToModel(Model model, Principal principal){
        model.addAttribute("genres", genreService.findAll());
        model.addAttribute("userDto", new UserDto());
        if(principal!=null) {
            model.addAttribute("user", userService.findByLogin(principal.getName()));
        }
    }

    @GetMapping()
    public String getCatalog(@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                             @RequestParam(value = "size", required = false, defaultValue = "18") int size,
                             BookPage bookPage, Model model, SessionStatus sessionStatus){
        if(bookPage==null) bookPage = new BookPage();
        model.addAttribute("bookPage", bookPage);
        model.addAttribute("genresOn", genreService.findBySetId(bookPage.getGenreSet()));
        model.addAttribute("books", bookService.getPage(pageNumber, size, bookPage));
        return "bookCatalog";
    }


    @GetMapping("/rating")
    public String getCatalogRating(@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                                   @RequestParam(value = "size", required = false, defaultValue = "18") int size,
                                   BookPage bookPage, Model model){
        bookPage.setSortBy("rating");
        bookPage.setSortDirection(Sort.Direction.DESC);
        model.addAttribute("genresOn", genreService.findBySetId(bookPage.getGenreSet()));
        model.addAttribute("books", bookService.getPage(pageNumber, size, bookPage));
        return "redirect:/catalog";
    }
    @GetMapping("/title")
    public String getCatalogTitle(@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                                  @RequestParam(value = "size", required = false, defaultValue = "18") int size,
                                  BookPage bookPage, Model model){
        bookPage.setSortBy("title");
        bookPage.setSortDirection(Sort.Direction.ASC);
        model.addAttribute("genresOn", genreService.findBySetId(bookPage.getGenreSet()));
        model.addAttribute("books", bookService.getPage(pageNumber, size, bookPage));
        return "redirect:/catalog";
    }
    @GetMapping("/year")
    public String getCatalogYearPublished(@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                                          @RequestParam(value = "size", required = false, defaultValue = "18") int size,
                                          BookPage bookPage, Model model){
        bookPage.setSortBy("yearPublished");
        bookPage.setSortDirection(Sort.Direction.ASC);
        model.addAttribute("genresOn", genreService.findBySetId(bookPage.getGenreSet()));
        model.addAttribute("books", bookService.getPage(pageNumber, size, bookPage));
        return "redirect:/catalog";
    }
    @GetMapping("/date")
    public String getCatalogDate(@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                                          @RequestParam(value = "size", required = false, defaultValue = "18") int size,
                                          BookPage bookPage, Model model){
        bookPage.setSortBy("id");
        bookPage.setSortDirection(Sort.Direction.ASC);
        model.addAttribute("genresOn", genreService.findBySetId(bookPage.getGenreSet()));
        model.addAttribute("books", bookService.getPage(pageNumber, size, bookPage));
        return "redirect:/catalog";
    }

    @PostMapping("/revers")
    public String revers(@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                                 @RequestParam(value = "size", required = false, defaultValue = "18") int size,
                                 BookPage bookPage, Model model){
        Sort.Direction sortDirection = bookPage.getSortDirection();
        bookPage.setSortDirection(
                sortDirection==Sort.Direction.DESC?
                        Sort.Direction.ASC : Sort.Direction.DESC);
        model.addAttribute("genresOn", genreService.findBySetId(bookPage.getGenreSet()));
        model.addAttribute("books", bookService.getPage(pageNumber, size, bookPage));
        return "bookCatalog";
    }

    @GetMapping("/search")
    public String getSearch(@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                                 @RequestParam(value = "size", required = false, defaultValue = "18") int size,
                                 BookPage bookPage, Model model, String keyword){
        if(keyword!=null) {
            bookPage.setSearch(keyword);
            bookPage.setSortBy("title");
            bookPage.setSortDirection(Sort.Direction.ASC);
            model.addAttribute("bookPage", bookPage);
            model.addAttribute("genresOn", genreService.findBySetId(bookPage.getGenreSet()));
            model.addAttribute("books", bookService.getByKeyword(pageNumber, size, bookPage));
        }else {
            bookPage.setSortBy("title");
            bookPage.setSortDirection(Sort.Direction.ASC);
            model.addAttribute("bookPage", bookPage);
            model.addAttribute("books", bookService.getPage(pageNumber, size, bookPage));
        }
        return "bookCatalog";
    }

    @GetMapping("/clear")
    public String getClearGenre(BookPage bookPage){
        bookPage.clearSet();
        return "redirect:/catalog";
    }

}
