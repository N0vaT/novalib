package ru.nova.novalib.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import ru.nova.novalib.domain.Book;
import ru.nova.novalib.domain.BookPage;
import ru.nova.novalib.domain.paging.Paged;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookServiceTest {

    @Autowired
    private BookService bookService;

    @Test
    void getByGenreId() {
        BookPage bp = new BookPage();
        bp.addGenre(3L);
        bp.addGenre(2L);
        bp.addGenre(44L);
        Paged<Book> byGenreId = bookService.getByGenreId(1,10, bp);
        byGenreId.getPage().forEach(b-> System.out.println(b.getTitle()));
    }
}