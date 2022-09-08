package ru.nova.novalib.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.nova.novalib.dao.ChapterRepository;
import ru.nova.novalib.domain.Book;
import ru.nova.novalib.domain.Chapter;
import ru.nova.novalib.domain.paging.Paged;
import ru.nova.novalib.domain.paging.Paging;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Service
public class ChapterService {

    private ChapterRepository chapterRepository;

    public ChapterService(ChapterRepository chapterRepository) {
        this.chapterRepository = chapterRepository;
    }

    public Chapter getChapterByChapterId(Long chapterId){
        return chapterRepository.findByChapterId(chapterId);
    }

    public List<Chapter> getChaptersPage(Book book){
        Sort sort = Sort.by(Sort.Direction.ASC, "numberInBook");
        return StreamSupport
                .stream(chapterRepository.findAllByBook(book, sort).spliterator(), false)
                .collect(Collectors.toList());
    }

    public void deleteById(Long chapterId) {
        chapterRepository.deleteById(chapterId);
    }

    public Paged<Chapter> getPage(Book book, int pageNumber, int size){
        PageRequest request = PageRequest.of(pageNumber - 1, size, Sort.Direction.ASC, "numberInBook");
        Page<Chapter> chapterPage = chapterRepository.findAllByBook(book, request);
        return new Paged<>(chapterPage, Paging.of(chapterPage.getTotalPages(), pageNumber, size));
    }

    public Chapter getFirst(Book book) {
        return getChaptersPage(book).get(0);
    }
}
