package ru.nova.novalib.domain;

import lombok.*;

import javax.persistence.*;



@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table(name = "nl_author")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "author_id")
    private Long authorId;
    @Column(name = "author_name")
    private String authorName;

    public Author(String authorName) {
        this.authorName = authorName;
    }
}
