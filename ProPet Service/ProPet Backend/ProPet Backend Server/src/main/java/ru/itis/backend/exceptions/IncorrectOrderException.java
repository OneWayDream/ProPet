package ru.itis.backend.exceptions;

public class IncorrectOrderException extends SearchException {
    public IncorrectOrderException() {
    }

    public IncorrectOrderException(String message) {
        super(message);
    }

    public IncorrectOrderException(String message, Throwable cause) {
        super(message, cause);
    }

    public IncorrectOrderException(Throwable cause) {
        super(cause);
    }
}
