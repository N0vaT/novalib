package ru.nova.novalib.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nova.novalib.domain.Book;
import ru.nova.novalib.domain.Chapter;

import java.util.List;

@Repository
public interface ChapterRepository extends JpaRepository<Chapter, Long> {
    List<Chapter> findAllByBook(Book book, Sort sort);
    Page<Chapter> findAllByBook(Book book, PageRequest pageRequest);

    Chapter findByChapterId(Long chapterId);
}
