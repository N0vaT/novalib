package ru.nova.novalib.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.nova.novalib.domain.Book;
import ru.nova.novalib.domain.Chapter;
import ru.nova.novalib.service.BookService;
import ru.nova.novalib.service.ChapterService;

import java.util.List;

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
        model.addAttribute("chaptersSort", chapterService.getChaptersPage(book));
        model.addAttribute("book", book);
        return "book";
    }

    @GetMapping("/{bookId}/{chapterId}")
    public String findChapterById(@PathVariable(name = "bookId") Long bookId, @PathVariable(name = "chapterId") Long chapterId, Model model){
        Book book = bookService.findById(bookId);
        if(book == null){
            return "";
        }
        List<Chapter> chaptersPage = chapterService.getChaptersPage(book);
        Chapter chapterByChapterId = chapterService.getChapterByChapterId(chapterId);
        int index = chaptersPage.indexOf(chapterByChapterId);
        Long previousId = null;
        Long nextId = null;
        if(chaptersPage.listIterator(index).hasPrevious()) {
            previousId = chaptersPage.listIterator(index).previous().getChapterId();
        }
        if(chaptersPage.listIterator(index+1).hasNext()) {
            nextId = chaptersPage.listIterator(index+1).next().getChapterId();
        }
        model.addAttribute("chapterPage", chapterByChapterId);
        model.addAttribute("chaptersPage",  chaptersPage);
        model.addAttribute("book", book);
        model.addAttribute("previousId", previousId);
        model.addAttribute("nextId", nextId);
        return "bookPage";
    }

    @DeleteMapping("/{bookId}")
    public String deleteBook(@PathVariable(name = "bookId") Long bookId){
        bookService.deleteBook(bookId);
        return "redirect:/admin";
    }


    @GetMapping("/random")
    public String findBookById(){
        return "redirect:/book/"+bookService.random();
    }
}
