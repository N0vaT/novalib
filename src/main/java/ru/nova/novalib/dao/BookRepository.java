package ru.nova.novalib.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nova.novalib.domain.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}
