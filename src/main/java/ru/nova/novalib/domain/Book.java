package ru.nova.novalib.domain;

import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class Book {

    private Long id;
    private String title;
    private List<Author> authorList = new ArrayList<>();
    private String publisher;
    private List<Genre> genre;
    private LocalDate published;
    private String country;
    private String description;
    private String fileName;

    public void addAuthor(Author author){
        authorList.add(author);
    }
}
