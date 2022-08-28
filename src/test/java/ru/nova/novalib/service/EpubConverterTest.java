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

    }
}