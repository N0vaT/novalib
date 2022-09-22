package ru.nova.novalib.controller;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.nova.novalib.domain.Book;
import ru.nova.novalib.domain.BookPage;
import ru.nova.novalib.domain.User;
import ru.nova.novalib.domain.dto.UserDto;
import ru.nova.novalib.service.BookService;
import ru.nova.novalib.service.BookmarkService;
import ru.nova.novalib.service.UserService;

import java.security.Principal;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/user/bookmark")
@SessionAttributes({"bookPage", "user", "books"})
public class BookmarkController {

        private final BookService bookService;
        private final UserService userService;
        private final BookmarkService bookmarkService;

    public BookmarkController(BookService bookService, UserService userService, BookmarkService bookmarkService) {
        this.bookService = bookService;
        this.userService = userService;
        this.bookmarkService = bookmarkService;
    }

    @ModelAttribute
        public void addToModel(Model model) {
        model.addAttribute("userDto", new UserDto());
    }

        @GetMapping()
        public String getBookmark(@RequestParam(value = "type", required = false, defaultValue = "1") int type,
                                 BookPage bookPage, Model model, Principal principal){
            if(bookPage==null) bookPage = new BookPage();
            if(type!=1) bookPage.setType(type);
            model.addAttribute("bookPage", bookPage);
            if (principal != null) {
                User user = userService.findByLogin(principal.getName());
                model.addAttribute("user", user);
                model.addAttribute("userBookmarks", user.getUserBookmarks());
                model.addAttribute("books", bookmarkService.getBookmarkPage(user, bookPage));
            }
            return "bookmark";
        }

        @GetMapping("/rating")
        public String getByRating(BookPage bookPage){
            bookPage.setSortBy("rating");
            bookPage.setSortDirection(Sort.Direction.DESC);;
            return "redirect:/user/bookmark";
        }
        @GetMapping("/title")
        public String getByTitle(BookPage bookPage){
            bookPage.setSortBy("title");
            bookPage.setSortDirection(Sort.Direction.ASC);
            return "redirect:/user/bookmark";
        }
        @GetMapping("/year")
        public String getByYearPublished(BookPage bookPage){
            bookPage.setSortBy("yearPublished");
            bookPage.setSortDirection(Sort.Direction.ASC);
            return "redirect:/user/bookmark";
        }
        @GetMapping("/date")
        public String getByDate(BookPage bookPage){
            bookPage.setSortBy("id");
            bookPage.setSortDirection(Sort.Direction.ASC);
            return "redirect:/user/bookmark";
        }
    @GetMapping("/chapters")
    public String getByChapters(BookPage bookPage){
        bookPage.setSortBy("chapters");
        bookPage.setSortDirection(Sort.Direction.ASC);
        return "redirect:/user/bookmark";
    }

        @PostMapping()
        public String revers(@RequestParam(value = "type", required = false, defaultValue = "1") int type,
                             BookPage bookPage, Model model, Principal principal){
            if(bookPage==null) bookPage = new BookPage();
            if(type!=1) bookPage.setType(type);
            model.addAttribute("bookPage", bookPage);
            if (principal != null) {
                User user = userService.findByLogin(principal.getName());
                model.addAttribute("user", user);
                model.addAttribute("userBookmarks", user.getUserBookmarks());
                List<Book> bookmarkPage = (List<Book>) model.getAttribute("books");
                if(bookmarkPage!=null) {
                    Collections.reverse(bookmarkPage);
                    model.addAttribute("books", bookmarkPage);
                }
            }
            return "bookmark";
        }

    @GetMapping("/add/{bookId}/{type}")
    public String addBook(@PathVariable(name = "bookId") long bookId, @PathVariable(name = "type") int type, Principal principal){
        User user = userService.findByLogin(principal.getName());
        bookmarkService.saveBookInBookmark(user, type, bookId);
        return "redirect:/book/" + bookId;
    }

}
