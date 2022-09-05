package ru.nova.novalib.exception;

import java.text.MessageFormat;

public class AuthorNotFoundException extends RuntimeException {
    public AuthorNotFoundException(long id) {
        super(MessageFormat.format("Could not find author with id: {0}", id));
    }
}