package ru.itis.backend.exceptions.persistence;

import javax.persistence.PersistenceException;

public class TreatyInfoAlreadyExistsException extends PersistenceException {

    public TreatyInfoAlreadyExistsException() {
        super();
    }

    public TreatyInfoAlreadyExistsException(String message) {
        super(message);
    }

    public TreatyInfoAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public TreatyInfoAlreadyExistsException(Throwable cause) {
        super(cause);
    }
}
