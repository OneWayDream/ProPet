package ru.itis.backend.exceptions.persistence;

import javax.persistence.PersistenceException;

public class EntityNotExistsException extends PersistenceException {

    public EntityNotExistsException() {
        super();
    }

    public EntityNotExistsException(String message) {
        super(message);
    }

    public EntityNotExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityNotExistsException(Throwable cause) {
        super(cause);
    }
}
