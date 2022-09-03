package ru.nova.novalib.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.nova.novalib.dao.ChapterRepository;
import ru.nova.novalib.domain.Book;
import ru.nova.novalib.domain.Chapter;

import java.util.List;


@Service
public class ChapterService {

    private ChapterRepository chapterRepository;

    public ChapterService(ChapterRepository chapterRepository) {
        this.chapterRepository = chapterRepository;
    }

    public List<Chapter> getChaptersByBookIdSorted(Book book){
        return chapterRepository.findAllByBook(book, Sort.by("numberInBook"));
    }

    public Chapter getChapterByChapterId(Long chapterId){
        return chapterRepository.findByChapterId(chapterId);
    }

    public List<Chapter> getChaptersPage(Book book){
        Sort sort = Sort.by(Sort.Direction.ASC, "numberInBook");
        return chapterRepository.findAllByBook(book, sort);
    }


}
