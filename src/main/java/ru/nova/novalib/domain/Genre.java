package ru.nova.novalib.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"genreId"})
@Entity
@Table(name = "nl_genre")
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "genre_id")
    private Long genreId;
    @Column(name = "genre_title")
    private String genreTitle;

    public Genre(String genreTitle) {
        this.genreTitle = genreTitle;
    }
}
