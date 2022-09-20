package ru.nova.novalib.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table(name = "nl_bookmark")
public class Bookmark {

    public Bookmark(String bookmarkName, Type type, User user) {
        this.bookmarkName = bookmarkName;
        this.type = type;
        this.user = user;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookmark_id")
    private Long bookmarkId;
    @Column(name = "bookmark_name")
    private String bookmarkName;
    @Column(name = "bookmark_type")
    @Enumerated(EnumType.STRING)
    private Type type;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinTable(name = "nl_book_bookmark",
            joinColumns = @JoinColumn(name = "bookmark_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id"))
    private List<Book> books = new ArrayList<>();

    public void addBook(Book book){
        books.add(book);
    }

    public enum Type {
        READING, WILL, FINISHED, STOPPED, NOT_INTERESTING
    }

}
