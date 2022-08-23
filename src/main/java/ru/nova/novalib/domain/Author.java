package ru.nova.novalib.domain;

import lombok.Data;

import java.util.List;

@Data
public class Author {

    private Long id;
    private String name;
    private List<Book> bookList;
}
