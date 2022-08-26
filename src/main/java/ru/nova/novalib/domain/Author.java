package ru.nova.novalib.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "nl_author")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "author_id")
    private Long authorId;
    @Column(name = "author_name")
    private String authorName;

}
