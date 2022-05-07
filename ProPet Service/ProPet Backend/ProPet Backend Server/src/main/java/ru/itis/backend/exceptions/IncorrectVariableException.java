package ru.itis.backend.exceptions;

public class IncorrectVariableException extends SearchException {

    public IncorrectVariableException() {
        super();
    }

    public IncorrectVariableException(String message) {
        super(message);
    }

    public IncorrectVariableException(String message, Throwable cause) {
        super(message, cause);
    }

    public IncorrectVariableException(Throwable cause) {
        super(cause);
    }
}
