package ru.nova.novalib.exception;

import java.text.MessageFormat;

public class GenreNotFoundException extends RuntimeException {
    public GenreNotFoundException(long id) {
        super(MessageFormat.format("Could not find genre with id: {0}", id));
    }
}
