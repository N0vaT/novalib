package ru.nova.novalib.service;

import org.springframework.beans.factory.annotation.Autowired;
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
        bookRepository.saveAndFlush(book);
    }

    @Transactional(readOnly = true)
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Book findById(Long id){
        return bookRepository.findById(id).orElse(null);
    }
}
