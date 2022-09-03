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

import java.util.List;

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
        return bookRepository.findAll(sort);
    }

    @Transactional(readOnly = true)
    public Book findById(Long id){
        return bookRepository.findById(id).orElse(null);
    }

//    public Page<Book> getBooks(BookPage bookPage){
//        Sort sort = Sort.by(bookPage.getSortDirection(), bookPage.getSortBy());
//        Pageable pageable = PageRequest.of(bookPage.getPageNumber(), bookPage.getPageSize(), sort);
//        return bookRepository.findAll(pageable);
//    }
}
