package ru.nova.novalib.domain;

import lombok.*;

import javax.persistence.*;
import java.util.*;

@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
@NoArgsConstructor
@Entity
@Table(name = "nl_book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long id;
    @Column(name = "book_title")
    private String title;
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinTable(name = "nl_book_author",
            joinColumns = @JoinColumn(name = "book_id",nullable = false),
            inverseJoinColumns = @JoinColumn(name = "author_id", nullable = false))
    private Set<Author> authors = new HashSet<>();
    @Column(name = "book_publisher")
    private String publisher;
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinTable(name = "nl_book_genre",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private Set<Genre> genres = new HashSet<>();
    @Column(name = "book_year_published")
    private String yearPublished;
    @Column(name = "book_country")
    private String country;
    @Column(name = "book_description")
    private String description;
    @Column(name = "book_file_name")
    private String fileName;
    @Column(name = "book_poster_name")
    private String posterName;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "book_id",nullable = false)
    private List<Chapter> chapters = new ArrayList<>();
    @Column(name = "book_rating")
    private Double rating;



    public void addAuthor(Author author){
        authors.add(author);
    }

    public void deleteAuthor(Author author){
        authors.remove(author);
    }

    public void addGenre(Genre genre){
        genres.add(genre);
    }

    public void deleteGenre(Genre genre){
        genres.remove(genre);
    }

    public void addChapter(Chapter chapter){
    chapters.add(chapter);
    }

}
