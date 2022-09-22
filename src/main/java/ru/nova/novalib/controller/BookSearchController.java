package ru.nova.novalib.controller;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.nova.novalib.domain.BookPage;
import ru.nova.novalib.domain.Bookmark;
import ru.nova.novalib.domain.User;
import ru.nova.novalib.domain.dto.UserDto;
import ru.nova.novalib.service.BookService;
import ru.nova.novalib.service.UserService;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/search")
public class BookSearchController {

    private final BookService bookService;
    private final UserService userService;

    public BookSearchController(BookService bookService, UserService userService) {
        this.bookService = bookService;
        this.userService = userService;
    }

    @ModelAttribute
    public void addGenresToModel(Model model, Principal principal){
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
    public String getSearch(@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                            @RequestParam(value = "size", required = false, defaultValue = "24") int size,
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
        return "search";
    }


}
