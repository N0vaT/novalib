package ru.nova.novalib.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.nova.novalib.dao.AuthorRepository;
import ru.nova.novalib.domain.Author;

@Slf4j
@Service
public class AuthorService {

    private AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Author existByName(String authorName){
        System.out.println("");
        if(authorRepository.existsByAuthorName(authorName)){
            Author a = authorRepository.findByAuthorName(authorName);
            log.info("Добавлен автор из БД - {}", a);
            return a;
        }else{
            Author author = new Author();
            author.setAuthorName(authorName);
            log.info("Создан новый автор - {}", author);
            return authorRepository.save(author);
        }
    }


}
