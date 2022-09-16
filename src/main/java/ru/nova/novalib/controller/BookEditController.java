package ru.nova.novalib.controller;

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

@Controller
@RequestMapping("/book/edit")
@SessionAttributes("book")
public class BookEditController {

    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;
    private final FileUploadService fileUploadService;
    private final ChapterService chapterService;
    private final UserService userService;

    public BookEditController(BookService bookService, AuthorService authorService, GenreService genreService, FileUploadService fileUploadService, ChapterService chapterService, UserService userService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.genreService = genreService;
        this.fileUploadService = fileUploadService;
        this.chapterService = chapterService;
        this.userService = userService;
    }

    @ModelAttribute
    public void addGenresToModel(Model model, Principal principal) {
        model.addAttribute("userDto", new UserDto());
        if(principal!=null) {
            model.addAttribute("user", userService.findByLogin(principal.getName()));
        }
    }

    @GetMapping("/{bookId}")
    public String editBookPage(@PathVariable(name = "bookId") Long bookId,
                               @RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                               @RequestParam(value = "size", required = false, defaultValue = "100") int size, Model model){
        Book book = bookService.findById(bookId);
        model.addAttribute("book", book);
        model.addAttribute("author", new Author());
        model.addAttribute("genre", new Genre());
        model.addAttribute("genres", genreService.findAll());
        model.addAttribute("chapters", chapterService.getPage(book, pageNumber, size));
        return "editBook";
    }

    @PutMapping("/{bookId}/title")
    public String editTitle(@PathVariable(name = "bookId") Long bookId, Book book, SessionStatus sessionStatus){
        bookService.save(book);
        sessionStatus.setComplete();
        return "redirect:/book/edit/" + bookId;
    }

    @DeleteMapping("/{bookId}/author/{authorId}")
    public String editAuthors(@PathVariable(name = "bookId") Long bookId, @PathVariable(name = "authorId") Long authorId, Book book, SessionStatus sessionStatus){
        bookService.save(bookService.deleteAuthor(book, authorId));
        sessionStatus.setComplete();
        return "redirect:/book/edit/" + bookId;
    }
    @PostMapping("/{bookId}/author")
    public String addAuthor(@PathVariable(name = "bookId") Long bookId, Author author, Book book, SessionStatus sessionStatus){
        book.addAuthor(authorService.existByName(author.getAuthorName()));
        bookService.save(book);
        sessionStatus.setComplete();
        return "redirect:/book/edit/" + bookId;
    }

    @PutMapping("/{bookId}/publisher")
    public String editPublisher(@PathVariable(name = "bookId") Long bookId, Book book, SessionStatus sessionStatus){
        bookService.save(book);
        sessionStatus.setComplete();
        return "redirect:/book/edit/" + bookId;
    }

    @DeleteMapping("/{bookId}/genre/{genreId}")
    public String editGenre(@PathVariable(name = "bookId") Long bookId, @PathVariable(name = "genreId") Long genreId, Book book, SessionStatus sessionStatus){
        bookService.save(bookService.deleteGenre(book, genreId));
        sessionStatus.setComplete();
        return "redirect:/book/edit/" + bookId;
    }
    @PostMapping("/{bookId}/genre")
    public String addGenre(@PathVariable(name = "bookId") Long bookId, Genre genre, Book book, SessionStatus sessionStatus){
        book.addGenre(genreService.findById(genre.getGenreId()));
        bookService.save(book);
        sessionStatus.setComplete();
        return "redirect:/book/edit/" + bookId;
    }

    @PutMapping("/{bookId}/yearPublished")
    public String editYearPublished(@PathVariable(name = "bookId") Long bookId, Book book, SessionStatus sessionStatus){
        bookService.save(book);
        sessionStatus.setComplete();
        return "redirect:/book/edit/" + bookId;
    }

    @PutMapping("/{bookId}/country")
    public String editCountry(@PathVariable(name = "bookId") Long bookId, Book book, SessionStatus sessionStatus){
        bookService.save(book);
        sessionStatus.setComplete();
        return "redirect:/book/edit/" + bookId;
    }

    @PutMapping("/{bookId}/description")
    public String editDescription(@PathVariable(name = "bookId") Long bookId, Book book, SessionStatus sessionStatus){
        bookService.save(book);
        sessionStatus.setComplete();
        return "redirect:/book/edit/" + bookId;
    }

    @PutMapping("/{bookId}/poster")
    public String editPoster(@PathVariable(name = "bookId") Long bookId, @RequestParam("file") MultipartFile file, Book book, SessionStatus sessionStatus){
        fileUploadService.savePoster(file, book);
        bookService.save(book);
        sessionStatus.setComplete();
        return "redirect:/book/edit/" + bookId;
    }
    @DeleteMapping("/{bookId}/chapter/{chapterId}")
    public String editChapter(@PathVariable(name = "bookId") Long bookId, @PathVariable(name = "chapterId") Long chapterId){
        chapterService.deleteById(chapterId);
        return "redirect:/book/edit/" + bookId;
    }

    @PutMapping("/{bookId}/rating")
    public String editRating(@PathVariable(name = "bookId") Long bookId, Book book, SessionStatus sessionStatus){
        bookService.save(book);
        sessionStatus.setComplete();
        return "redirect:/book/edit/" + bookId;
    }
}
