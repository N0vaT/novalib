package ru.nova.novalib.controller;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.nova.novalib.domain.Book;
import ru.nova.novalib.domain.BookPage;
import ru.nova.novalib.domain.Bookmark;
import ru.nova.novalib.domain.User;
import ru.nova.novalib.domain.dto.UserDto;
import ru.nova.novalib.service.BookService;
import ru.nova.novalib.service.BookmarkService;
import ru.nova.novalib.service.UserService;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/user/bookmark")
@SessionAttributes({"bookPage", "books"})
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
        public String getBookmark(@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                                 @RequestParam(value = "size", required = false, defaultValue = "18") int size,
                                 @RequestParam(value = "type", required = false, defaultValue = "1") int type,
                                 BookPage bookPage, Model model, Principal principal){
            if(bookPage==null) bookPage = new BookPage();
            bookPage.setType(type);
            model.addAttribute("bookPage", bookPage);
            if (principal != null) {
                User user = userService.findByLogin(principal.getName());
                model.addAttribute("user", user);
                model.addAttribute("userBookmarks", user.getUserBookmarks());
                model.addAttribute("books", bookmarkService.getBookmarkPage(user, pageNumber, size, bookPage));
                for(Bookmark.Type t: Bookmark.Type.values()){
                    Bookmark byUserAndType = bookmarkService.findByUserAndType(user, type);
                    if(byUserAndType==null){
                        model.addAttribute(t.toString().toLowerCase(), user.getUserBookmarks());
                    }else {
                        model.addAttribute(t.toString().toLowerCase(), byUserAndType);
                    }
                }
            }
            return "bookmark";
        }

        @GetMapping("/rating")
        public String getCatalogRating(@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                                       @RequestParam(value = "size", required = false, defaultValue = "18") int size,
                                       BookPage bookPage, Model model){
            bookPage.setSortBy("rating");
            bookPage.setSortDirection(Sort.Direction.DESC);
            model.addAttribute("books", bookService.getPage(pageNumber, size, bookPage));
            return "redirect:/catalog";
        }
        @GetMapping("/title")
        public String getCatalogTitle(@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                                      @RequestParam(value = "size", required = false, defaultValue = "18") int size,
                                      BookPage bookPage, Model model){
            bookPage.setSortBy("title");
            bookPage.setSortDirection(Sort.Direction.ASC);
            model.addAttribute("books", bookService.getPage(pageNumber, size, bookPage));
            return "redirect:/catalog";
        }
        @GetMapping("/year")
        public String getCatalogYearPublished(@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                                              @RequestParam(value = "size", required = false, defaultValue = "18") int size,
                                              BookPage bookPage, Model model){
            bookPage.setSortBy("yearPublished");
            bookPage.setSortDirection(Sort.Direction.ASC);
            model.addAttribute("books", bookService.getPage(pageNumber, size, bookPage));
            return "redirect:/catalog";
        }
        @GetMapping("/date")
        public String getCatalogDate(@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                                     @RequestParam(value = "size", required = false, defaultValue = "18") int size,
                                     BookPage bookPage, Model model){
            bookPage.setSortBy("id");
            bookPage.setSortDirection(Sort.Direction.ASC);
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
                model.addAttribute("books", bookService.getByKeyword(pageNumber, size, bookPage));
            }else {
                bookPage.setSortBy("title");
                bookPage.setSortDirection(Sort.Direction.ASC);
                model.addAttribute("bookPage", bookPage);
                model.addAttribute("books", bookService.getPage(pageNumber, size, bookPage));
            }
            return "bookCatalog";
        }

    @GetMapping("/add/{bookId}/{type}")
    public String addBook(@PathVariable(name = "bookId") long bookId, @PathVariable(name = "type") int type, Book book, Principal principal){
        User user = userService.findByLogin(principal.getName());
        user.getUserBookmarks().get(type-1).addBook(bookService.findById(bookId));
        userService.save(user);
        return "redirect:/book/" + bookId;
    }

}
