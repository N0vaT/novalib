package ru.nova.novalib.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nova.novalib.domain.Book;
import ru.nova.novalib.domain.Bookmark;
import ru.nova.novalib.domain.User;

import java.util.List;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    List<Bookmark> findAllByUser(User user);

    Bookmark findByUserAndType(User user, Bookmark.Type value);
}
