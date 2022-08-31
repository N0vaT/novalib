package ru.nova.novalib.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "nl_chapters")
public class Chapter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chapter_id")
    private Long chapterId;
    @Column(name = "chapter_name")
    private String chapterName;
    @Column(name = "chapter_text")
    private String chapterText;
    @Column(name = "number_in_book")
    private int numberInBook;
    @ManyToOne()
    @JoinColumn(name = "book_id", nullable = false, insertable = false,updatable = false)
    private Book book;

    public Chapter(String chapterName, String chapterText, int numberInBook) {
        this.chapterName = chapterName;
        this.chapterText = chapterText;
        this.numberInBook = numberInBook;
    }
}
