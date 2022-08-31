package ru.nova.novalib.dao;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nova.novalib.domain.Book;
import ru.nova.novalib.domain.Chapter;

import java.util.List;

@Repository
public interface ChapterRepository extends JpaRepository<Chapter, Long> {
    List<Chapter> findAllByBook(Book book, Sort sort);

    Chapter findByBookAndChapterId(Book book, Long chapterId);
}
