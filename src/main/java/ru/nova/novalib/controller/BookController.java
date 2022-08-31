package ru.nova.novalib.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.nova.novalib.domain.Book;
import ru.nova.novalib.service.BookService;
import ru.nova.novalib.service.ChapterService;

@Controller
@RequestMapping("/book")
public class BookController {

    private BookService bookService;
    private ChapterService chapterService;

    public BookController(BookService bookService, ChapterService chapterService) {
        this.bookService = bookService;
        this.chapterService = chapterService;
    }

    @GetMapping("/{id}")
    public String findBookById(@PathVariable(name = "id") Long id, Model model){
        Book book = bookService.findById(id);
        if(book == null){
            return "";
        }
        model.addAttribute("chaptersSort", chapterService.getChaptersByIdSorted(book));
        model.addAttribute("book", book);
        return "book";
    }
}
