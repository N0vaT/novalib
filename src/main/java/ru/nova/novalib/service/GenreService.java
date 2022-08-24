package ru.nova.novalib.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nova.novalib.dao.GenreRepository;
import ru.nova.novalib.domain.Genre;

import java.util.List;


@Service
public class GenreService {

    private GenreRepository genreRepository;

    @Autowired
    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public void save(Genre genre){
        genreRepository.save(genre);
    }

    public List<Genre> findAll() {
        System.out.println("");
        List<Genre> genres = genreRepository.findAll();
        System.out.println("");
        return genres;
    }
}
