package ru.nova.novalib.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "nl_book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long id;
    @Column(name = "book_title")
    private String title;
    @ManyToMany(cascade = CascadeType.REFRESH)
    @JoinTable(name = "nl_book_author",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    private Set<Author> authors = new TreeSet<>();
    @Column(name = "book_publisher")
    private String publisher;
    @ManyToMany(cascade = CascadeType.REFRESH)
    @JoinTable(name = "nl_book_genre",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private Set<Genre> genres = new HashSet<>();
    @Column(name = "book_date_of_published")
    private LocalDate published;
    @Column(name = "book_country")
    private String country;
    @Column(name = "book_description")
    private String description;
    @Column(name = "book_file_name")
    private String fileName;

    public void addAuthor(Author author){
        authors.add(author);
    }

    public void addGenre(Genre genre){
        genres.add(genre);
    }
}
