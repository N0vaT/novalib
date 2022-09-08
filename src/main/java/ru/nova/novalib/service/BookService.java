package ru.nova.novalib.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nova.novalib.dao.BookRepository;
import ru.nova.novalib.domain.Author;
import ru.nova.novalib.domain.Book;
import ru.nova.novalib.domain.BookPage;
import ru.nova.novalib.domain.Genre;
import ru.nova.novalib.domain.paging.Paged;
import ru.nova.novalib.domain.paging.Paging;
import ru.nova.novalib.exception.AuthorNotFoundException;
import ru.nova.novalib.exception.BookNotFoundException;
import ru.nova.novalib.exception.GenreNotFoundException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Service
public class BookService {

    @Value("${upload.path}")
    private String uploadPath;
    @Value("${upload.posterFile.path}")
    private String uploadPosterFilePath;

    private BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Transactional
    public void save(Book book){
        bookRepository.save(book);
    }

    @Transactional(readOnly = true)
    public List<Book> findAll() {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        return StreamSupport
                .stream(bookRepository.findAll(sort).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Book findById(Long id){
        return bookRepository.findById(id).orElseThrow(()->new BookNotFoundException(id));
    }

    public Paged<Book> getPage(int pageNumber, int size, BookPage bookPage){
        Sort.Direction sort = bookPage.getSortDirection();
        String sortBy = bookPage.getSortBy();
        PageRequest request = PageRequest.of(pageNumber - 1, size, sort, sortBy);
        Page<Book> pageBook = bookRepository.findAll(request);
        return new Paged<>(pageBook, Paging.of(pageBook.getTotalPages(), pageNumber, size));
    }

    @Transactional(readOnly = true)
    public long random() {
        List<Book> all = bookRepository.findAll();
        Collections.shuffle(all);
        int x = (int) (Math.random() * all.size());
        return all.get(x).getId();
    }

    @Transactional
    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
        String posterPath = uploadPosterFilePath + book.getPosterName();
        String filePath = uploadPath + book.getFileName();
        bookRepository.deleteById(id);
        try {
            Files.deleteIfExists(Paths.get(filePath));
            Files.deleteIfExists(Paths.get(posterPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.warn("Book with title: {}; id: {} is delete", book.getTitle(), id);
    }

    public Book deleteAuthor(Book book, Long authorId){
        Author author = book.getAuthors().stream().filter(a -> a.getAuthorId().equals(authorId)).findFirst().orElseThrow(() -> new AuthorNotFoundException(authorId));
        book.deleteAuthor(author);
        return book;
    }

    public Book deleteGenre(Book book, Long genreId) {
        Genre genre = book.getGenres().stream().filter(a -> a.getGenreId().equals(genreId)).findFirst().orElseThrow(() -> new GenreNotFoundException(genreId));
        book.deleteGenre(genre);
        return book;
    }

    public Paged<Book> getByKeyword(int pageNumber, int size, BookPage bookPage) {
        Sort.Direction sort = bookPage.getSortDirection();
        String sortBy = bookPage.getSortBy();
        PageRequest request = PageRequest.of(pageNumber - 1, size, sort, sortBy);
        Page<Book> pageBook = bookRepository.findByTitleContainingIgnoreCase(bookPage.getSearch(), request);
        return new Paged<>(pageBook, Paging.of(pageBook.getTotalPages(), pageNumber, size));
    }

    public Paged<Book> getByGenreId(int pageNumber, int size, BookPage bookPage) {
        Sort.Direction sort = bookPage.getSortDirection();
        String sortBy = bookPage.getSortBy();
        PageRequest request = PageRequest.of(pageNumber - 1, size, sort, sortBy);
        Page<Book> pageBook = bookRepository.findAllDistinctByGenresGenreIdIn(bookPage.getGenreSet(), request);
        return new Paged<>(pageBook, Paging.of(pageBook.getTotalPages(), pageNumber, size));
    }
}
