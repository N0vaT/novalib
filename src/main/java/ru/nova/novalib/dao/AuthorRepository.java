package ru.nova.novalib.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nova.novalib.domain.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    boolean existsByAuthorName(String authorName);

    Author findByAuthorName(String authorName);
}
