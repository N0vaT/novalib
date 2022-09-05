package ru.nova.novalib.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import ru.nova.novalib.domain.Author;
import ru.nova.novalib.domain.Book;
import ru.nova.novalib.domain.Genre;
import ru.nova.novalib.exception.AuthorNotFoundException;
import ru.nova.novalib.service.AuthorService;
import ru.nova.novalib.service.BookService;
import ru.nova.novalib.service.FileUploadService;
import ru.nova.novalib.service.GenreService;

@Controller
@RequestMapping("/book/edit")
@SessionAttributes("book")
public class BookEditController {

    private BookService bookService;
    private AuthorService authorService;
    private GenreService genreService;
    private FileUploadService fileUploadService;

    public BookEditController(BookService bookService, AuthorService authorService, GenreService genreService, FileUploadService fileUploadService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.genreService = genreService;
        this.fileUploadService = fileUploadService;
    }

    @GetMapping("/{bookId}")
    public String editBookPage(@PathVariable(name = "bookId") Long bookId, Model model){
        model.addAttribute("book", bookService.findById(bookId));
        model.addAttribute("author", new Author());
        model.addAttribute("genre", new Genre());
        model.addAttribute("genres", genreService.findAll());
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
}
