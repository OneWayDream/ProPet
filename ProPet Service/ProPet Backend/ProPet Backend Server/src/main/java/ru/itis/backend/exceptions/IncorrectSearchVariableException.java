package ru.itis.backend.exceptions;

public class IncorrectSearchVariableException extends SearchException {

    public IncorrectSearchVariableException() {
    }

    public IncorrectSearchVariableException(String message) {
        super(message);
    }

    public IncorrectSearchVariableException(String message, Throwable cause) {
        super(message, cause);
    }

    public IncorrectSearchVariableException(Throwable cause) {
        super(cause);
    }

}
