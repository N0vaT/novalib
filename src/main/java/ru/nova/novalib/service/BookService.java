package ru.nova.novalib.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nova.novalib.dao.BookRepository;
import ru.nova.novalib.domain.Book;
import ru.nova.novalib.domain.BookPage;
import ru.nova.novalib.domain.paging.Paged;
import ru.nova.novalib.domain.paging.Paging;
import ru.nova.novalib.exception.BookNotFoundException;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class BookService {

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

    public Page<Book> getBooks(BookPage bookPage){
        Sort sort = Sort.by(bookPage.getSortDirection(), bookPage.getSortBy());
        Pageable pageable = PageRequest.of(bookPage.getPageNumber(), bookPage.getPageSize(), sort);
        return bookRepository.findAll(pageable);
    }

    public Paged<Book> getPage(int pageNumber, int size){
        PageRequest request = PageRequest.of(pageNumber - 1, size, Sort.Direction.ASC, "id");
        Page<Book> bookPage = bookRepository.findAll(request);
        return new Paged<>(bookPage, Paging.of(bookPage.getTotalPages(), pageNumber, size));
    }
}
