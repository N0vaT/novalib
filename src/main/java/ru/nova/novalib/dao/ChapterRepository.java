package ru.nova.novalib.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nova.novalib.domain.Chapter;

@Repository
public interface ChapterRepository extends JpaRepository<Chapter, Long> {
}
