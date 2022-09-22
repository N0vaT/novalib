package ru.nova.novalib.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nova.novalib.dao.BookRepository;
import ru.nova.novalib.dao.BookmarkRepository;
import ru.nova.novalib.dao.UserRepository;
import ru.nova.novalib.domain.Book;
import ru.nova.novalib.domain.BookPage;
import ru.nova.novalib.domain.Bookmark;
import ru.nova.novalib.domain.User;
import ru.nova.novalib.exception.BookNotFoundException;

import java.util.Comparator;
import java.util.List;

@Service
public class BookmarkService {

    private BookmarkRepository bookmarkRepository;
    private BookRepository bookRepository;
    private UserRepository userRepository;

    public BookmarkService(BookmarkRepository bookmarkRepository, BookRepository bookRepository, UserRepository userRepository) {
        this.bookmarkRepository = bookmarkRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    public List<Bookmark> findByUser(User user) {
        return bookmarkRepository.findAllByUser(user);
    }

    public Bookmark findByUserAndType(User user, int type) {
        return bookmarkRepository.findByUserAndType(user, Bookmark.Type.values()[type - 1]);
    }

    public List<Book> getBookmarkPage(User user, BookPage bookPage) {
        List<Book> books = user.getUserBookmarks()
                .stream()
                .filter(b -> (b.getType().ordinal()) == bookPage.getType()-1).findFirst()
                .map(b -> b.getBooks()).get();
        return sortBy(books, bookPage);
    }


    public List<Book> sortBy(List<Book> books, BookPage bookPage){
        if(bookPage.getSortBy().equals("title")) {
            books.sort(new Comparator<Book>() {
                @Override
                public int compare(Book o1, Book o2) {
                    return o1.getTitle().compareTo(o2.getTitle());
                }
            });
        }
        if(bookPage.getSortBy().equals("rating")) {
            books.sort(new Comparator<Book>() {
                @Override
                public int compare(Book o1, Book o2) {
                    return o1.getRating().compareTo(o2.getRating());
                }
            });
        }
        if(bookPage.getSortBy().equals("yearPublished")) {
            books.sort(new Comparator<Book>() {
                @Override
                public int compare(Book o1, Book o2) {
                    return o1.getYearPublished().compareTo(o2.getYearPublished());
                }
            });
        }
        if(bookPage.getSortBy().equals("id")) {
            books.sort(new Comparator<Book>() {
                @Override
                public int compare(Book o1, Book o2) {
                    return o1.getId().compareTo(o2.getId());
                }
            });
        }
        if(bookPage.getSortBy().equals("chapters")) {
            books.sort(new Comparator<Book>() {
                @Override
                public int compare(Book o1, Book o2) {
                    return Integer.compare(o1.getChapters().size(), o2.getChapters().size());
                }
            });
        }
        return books;
    }

    @Transactional
    public void saveBookInBookmark(User user, int type, long bookId) {
        List<Bookmark> userBookmarks = user.getUserBookmarks();
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException(bookId));
        for (Bookmark bookmark:userBookmarks) {
            bookmark.getBooks().remove(book);
        }
        user.getUserBookmarks().get(type-1).addBook(book);
        userRepository.save(user);
    }
}
