package ru.itis.backend.exceptions;

public class DifferentPasswordsException extends RegistrationException {

    public DifferentPasswordsException() {
    }

    public DifferentPasswordsException(String message) {
        super(message);
    }

    public DifferentPasswordsException(String message, Throwable cause) {
        super(message, cause);
    }

    public DifferentPasswordsException(Throwable cause) {
        super(cause);
    }

}
