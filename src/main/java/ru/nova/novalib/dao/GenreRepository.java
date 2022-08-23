package ru.nova.novalib.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nova.novalib.domain.Genre;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {
}
