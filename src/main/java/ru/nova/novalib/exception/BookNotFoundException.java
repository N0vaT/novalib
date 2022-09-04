package ru.nova.novalib.exception;

import java.text.MessageFormat;

public class BookNotFoundException extends RuntimeException {

    public BookNotFoundException(Long id) {
        super(MessageFormat.format("Could not find book with id: {0}", id));
    }
}
