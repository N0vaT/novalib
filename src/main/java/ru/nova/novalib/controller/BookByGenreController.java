package ru.nova.novalib.controller;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import ru.nova.novalib.domain.Book;
import ru.nova.novalib.domain.BookPage;
import ru.nova.novalib.domain.Bookmark;
import ru.nova.novalib.domain.User;
import ru.nova.novalib.domain.dto.UserDto;
import ru.nova.novalib.domain.paging.Paged;
import ru.nova.novalib.service.BookService;
import ru.nova.novalib.service.GenreService;
import ru.nova.novalib.service.UserService;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/catalog/byGenre")
@SessionAttributes("bookPage")
public class BookByGenreController {

    private final BookService bookService;
    private final GenreService genreService;
    private final UserService userService;

    public BookByGenreController(BookService bookService, GenreService genreService, UserService userService) {
        this.bookService = bookService;
        this.genreService = genreService;
        this.userService = userService;
    }

    @ModelAttribute
    public void addGenresToModel(Model model, Principal principal){
        model.addAttribute("genres", genreService.findAll());
        model.addAttribute("userDto", new UserDto());
        if(principal!=null) {
            User user = userService.findByLogin(principal.getName());
            model.addAttribute("user", user);
            for(Bookmark.Type type: Bookmark.Type.values()){
                // Добавляю Map<Bookmark.Type, List<Long>>
                List<Long> bookIdList = user.getUserBookmarks().stream()
                        .filter(bookmark -> bookmark.getType() == type).findFirst().get().getBooks()
                        .stream().map(book -> book.getId()).toList();
                model.addAttribute(type.toString().toLowerCase(), bookIdList);
            }
        }
    }

    @GetMapping()
    public String getByGenre(@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                             @RequestParam(value = "size", required = false, defaultValue = "18") int size,
                             BookPage bookPage, Model model, SessionStatus sessionStatus){
        Paged<Book> byGenreId = bookService.getByGenreId(pageNumber, size, bookPage);
        model.addAttribute("genresOn", genreService.findBySetId(bookPage.getGenreSet()));
        model.addAttribute("books", bookService.getByGenreId(pageNumber, size, bookPage));
        return "bookByGenre";
    }

    @PostMapping()
    public String getByGenrePost(@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                             @RequestParam(value = "size", required = false, defaultValue = "18") int size,
                             BookPage bookPage, Model model){
        bookPage.setSortBy("rating");
        bookPage.setSortDirection(Sort.Direction.DESC);
        model.addAttribute("books", bookService.getByGenreId(pageNumber, size, bookPage));
        return "redirect:/catalog/byGenre";
    }

    @GetMapping("/rating")
    public String getCatalogRating(@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                                   @RequestParam(value = "size", required = false, defaultValue = "18") int size,
                                   BookPage bookPage, Model model){
        bookPage.setSortBy("rating");
        bookPage.setSortDirection(Sort.Direction.DESC);
        model.addAttribute("books", bookService.getByGenreId(pageNumber, size, bookPage));
        return "redirect:/catalog/byGenre";
    }
    @GetMapping("/title")
    public String getCatalogTitle(@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                                  @RequestParam(value = "size", required = false, defaultValue = "18") int size,
                                  BookPage bookPage, Model model){
        bookPage.setSortBy("title");
        bookPage.setSortDirection(Sort.Direction.ASC);
        model.addAttribute("books", bookService.getByGenreId(pageNumber, size, bookPage));
        return "redirect:/catalog/byGenre";
    }
    @GetMapping("/year")
    public String getCatalogYearPublished(@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                                          @RequestParam(value = "size", required = false, defaultValue = "18") int size,
                                          BookPage bookPage, Model model){
        bookPage.setSortBy("yearPublished");
        bookPage.setSortDirection(Sort.Direction.ASC);
        model.addAttribute("books", bookService.getByGenreId(pageNumber, size, bookPage));
        return "redirect:/catalog/byGenre";
    }
    @GetMapping("/date")
    public String getCatalogDate(@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                                 @RequestParam(value = "size", required = false, defaultValue = "18") int size,
                                 BookPage bookPage, Model model){
        bookPage.setSortBy("id");
        bookPage.setSortDirection(Sort.Direction.ASC);
        model.addAttribute("books", bookService.getByGenreId(pageNumber, size, bookPage));
        return "redirect:/catalog/byGenre";
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
        model.addAttribute("books", bookService.getByGenreId(pageNumber, size, bookPage));
        return "bookByGenre";
    }

    @GetMapping("/{genreId}")
    public String getByGenreById(@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                                 @RequestParam(value = "size", required = false, defaultValue = "18") int size,
                                 @PathVariable(name = "genreId") long genreId,
                                 BookPage bookPage, Model model){
        if(bookPage==null) bookPage = new BookPage();
        bookPage.addGenre(genreId);
        bookPage.setSortBy("rating");
        bookPage.setSortDirection(Sort.Direction.DESC);
        model.addAttribute("genresOn", genreService.findBySetId(bookPage.getGenreSet()));
        model.addAttribute("books", bookService.getByGenreId(pageNumber, size, bookPage));
        return "bookByGenre";
    }
    @GetMapping("/clear")
    public String getClearGenre(@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                                 @RequestParam(value = "size", required = false, defaultValue = "18") int size,
                                 BookPage bookPage, Model model){
        bookPage.clearSet();
        return "redirect:/catalog/byGenre";
    }
}
