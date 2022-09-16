package ru.nova.novalib.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import ru.nova.novalib.domain.Author;
import ru.nova.novalib.domain.Book;
import ru.nova.novalib.domain.Genre;
import ru.nova.novalib.domain.dto.UserDto;
import ru.nova.novalib.service.*;

import java.security.Principal;

@Slf4j
@Controller()
@RequestMapping("/admin/books/save")
@SessionAttributes("book")
public class SaveController {
    private final BookService bookService;
    private final GenreService genreService;
    private final AuthorService authorService;
    private final FileUploadService fileUploadService;
    private final UserService userService;

    public SaveController(BookService bookService, GenreService genreService, AuthorService authorService, FileUploadService fileUploadService, UserService userService) {
        this.bookService = bookService;
        this.genreService = genreService;
        this.authorService = authorService;
        this.fileUploadService = fileUploadService;
        this.userService = userService;
    }

    @ModelAttribute
    public void addGenresToModel(Model model, Principal principal){
        model.addAttribute("genreList", genreService.findAll());
        model.addAttribute("genreForm", new Genre());
        model.addAttribute("authorForm", new Author());
        model.addAttribute("userDto", new UserDto());
        if(principal!=null) {
            model.addAttribute("user", userService.findByLogin(principal.getName()));
        }
    }

    @GetMapping
    public String pageSave(){
        return "save-book";
    }

    @PostMapping
    public String save(Book book, Author author,  SessionStatus sessionStatus){
        bookService.save(book);
        log.info("Order submitted: {}", book);
        sessionStatus.setComplete();
        return "redirect:/admin";
    }

    @PostMapping("/genre")
    public String addGenre( Genre genre, @ModelAttribute Book book){
        book.addGenre(genreService.findById(genre.getGenreId()));
        return "redirect:/admin/books/save";
    }

    @PostMapping("/author")
    public String addAuthor( Author author, @ModelAttribute Book book){
        book.addAuthor(authorService.existByName(author.getAuthorName()));
        return "redirect:/admin/books/save";
    }

    @PostMapping("/bookPoster")
    public String addBook(@RequestParam("file") MultipartFile file, @ModelAttribute Book book){
        fileUploadService.savePoster(file, book);
        return "redirect:/admin/books/save";
    }


}
