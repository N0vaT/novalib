package ru.nova.novalib.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import ru.nova.novalib.dao.BookmarkRepository;
import ru.nova.novalib.domain.Book;
import ru.nova.novalib.domain.BookPage;
import ru.nova.novalib.domain.Bookmark;
import ru.nova.novalib.domain.User;
import ru.nova.novalib.domain.paging.Paged;
import ru.nova.novalib.domain.paging.Paging;

import java.util.List;

@Service
public class BookmarkService {

    private BookmarkRepository bookmarkRepository;

    public BookmarkService(BookmarkRepository bookmarkRepository) {
        this.bookmarkRepository = bookmarkRepository;
    }

    public List<Bookmark> findByUser(User user) {
        return bookmarkRepository.findAllByUser(user);
    }

    public Bookmark findByUserAndType(User user, int type) {
        return bookmarkRepository.findByUserAndType(user, Bookmark.Type.values()[type - 1]);
    }

    public Paged<Book> getBookmarkPage(User user, int pageNumber, int size, BookPage bookPage) {
        List<Book> books = user.getUserBookmarks()
                .stream()
                .filter(b -> (b.getType().ordinal()) == bookPage.getType()-1).findFirst()
                .map(b -> b.getBooks()).get();
        Page<Book> pageBook = new PageImpl<>(books);
        return new Paged<>(pageBook, Paging.of(pageBook.getTotalPages(), pageNumber, size));
    }
}
