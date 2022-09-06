package ru.nova.novalib.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nova.novalib.dao.GenreRepository;
import ru.nova.novalib.domain.Genre;

import java.util.List;

@Slf4j
@Service
public class GenreService {

    private GenreRepository genreRepository;


    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public void save(Genre genre){
        genreRepository.save(genre);
    }

    public List<Genre> findAll() {
        List<Genre> genres = genreRepository.findAll();
        return genres;
    }

    public Genre findById(Long id) {
        Genre genre = genreRepository.findById(id).orElse(null);
        return genre;
    }
}
