package ru.itis.backend.exceptions.persistence;

import javax.persistence.PersistenceException;

public class SitterInfoAlreadyExistsException extends PersistenceException {

    public SitterInfoAlreadyExistsException() {
        super();
    }

    public SitterInfoAlreadyExistsException(String message) {
        super(message);
    }

    public SitterInfoAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public SitterInfoAlreadyExistsException(Throwable cause) {
        super(cause);
    }
}
