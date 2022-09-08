package ru.nova.novalib.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nova.novalib.dao.GenreRepository;
import ru.nova.novalib.domain.Genre;
import ru.nova.novalib.exception.GenreNotFoundException;

import java.util.List;
import java.util.Set;

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
        Genre genre = genreRepository.findById(id).orElseThrow(()-> new GenreNotFoundException(id));
        return genre;
    }

    public List<Genre> findBySetId(Set<Long> setId) {
        if(setId == null) {
            return null;
        }
        List<Genre> genres = genreRepository.findAllDistinctByGenreIdIn(setId);
        return genres;
    }
}
