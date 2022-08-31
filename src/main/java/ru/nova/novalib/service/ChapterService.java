package ru.nova.novalib.service;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.nova.novalib.dao.ChapterRepository;
import ru.nova.novalib.domain.Book;
import ru.nova.novalib.domain.BookPage;
import ru.nova.novalib.domain.Chapter;

import java.util.List;


@Service
public class ChapterService {

    private ChapterRepository chapterRepository;

    public ChapterService(ChapterRepository chapterRepository) {
        this.chapterRepository = chapterRepository;
    }

    public List<Chapter> getChaptersByIdSorted(Book book){

        return chapterRepository.findAllByBook(book, Sort.by("numberInBook"));
    }


}
