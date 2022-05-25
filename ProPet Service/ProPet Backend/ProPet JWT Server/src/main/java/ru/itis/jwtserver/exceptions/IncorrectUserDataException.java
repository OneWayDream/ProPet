package ru.itis.jwtserver.exceptions;

public class IncorrectUserDataException extends AuthorizationException {
    public IncorrectUserDataException() {
    }

    public IncorrectUserDataException(String message) {
        super(message);
    }

    public IncorrectUserDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public IncorrectUserDataException(Throwable cause) {
        super(cause);
    }
}
