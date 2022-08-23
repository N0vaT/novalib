package ru.nova.novalib.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.nova.novalib.domain.Book;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class EpubConverterTest {

    @Autowired
    private EpubConverter epubConverter;

    @Test
    void converter() {
        Book b = new Book();
        b.setFileName("15191078-09c7-4a1e-99bd-0501adceaf5e_protivostoyanie_svyatogo.epub");
        epubConverter.converter(b);

    }
}