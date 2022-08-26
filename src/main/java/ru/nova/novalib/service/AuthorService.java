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

    public Author existByName(Author author){
        System.out.println("");
        if(authorRepository.existsByAuthorName(author.getAuthorName())){
            Author a = authorRepository.findByAuthorName(author.getAuthorName());
            log.info("Добавлен автор из БД - {}", a);
            return a;
        }else{
            log.info("Создан новый автор - {}", author);
            return authorRepository.save(author);
        }
    }


}
