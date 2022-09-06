package ru.nova.novalib.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode
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

}
