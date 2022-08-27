package ru.nova.novalib.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.awt.*;
import java.io.File;
import java.nio.file.Files;
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
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinTable(name = "nl_book_author",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    private Set<Author> authors = new HashSet<>();
    @Column(name = "book_publisher")
    private String publisher;
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinTable(name = "nl_book_genre",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private Set<Genre> genres = new HashSet<>();
    @Column(name = "book_date_of_published")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate published;
    @Column(name = "book_country")
    private String country;
    @Column(name = "book_description")
    private String description;
    @Column(name = "book_file_name")
    private String fileName;
    @Column(name = "book_poster_name")
    private String posterName;

    public void addAuthor(Author author){
        authors.add(author);
    }

    public void addGenre(Genre genre){
        genres.add(genre);
    }
}
