package ru.itis.backend.exceptions.search;

public class IncorrectSortingVariableException extends SearchException {

    public IncorrectSortingVariableException() {
        super();
    }

    public IncorrectSortingVariableException(String message) {
        super(message);
    }

    public IncorrectSortingVariableException(String message, Throwable cause) {
        super(message, cause);
    }

    public IncorrectSortingVariableException(Throwable cause) {
        super(cause);
    }
}
