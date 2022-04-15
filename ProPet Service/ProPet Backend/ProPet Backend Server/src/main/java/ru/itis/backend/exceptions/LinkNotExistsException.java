package ru.itis.backend.exceptions;

import javax.persistence.PersistenceException;

public class LinkNotExistsException extends PersistenceException {

    public LinkNotExistsException() {
        super();
    }

    public LinkNotExistsException(String message) {
        super(message);
    }

    public LinkNotExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public LinkNotExistsException(Throwable cause) {
        super(cause);
    }
}
