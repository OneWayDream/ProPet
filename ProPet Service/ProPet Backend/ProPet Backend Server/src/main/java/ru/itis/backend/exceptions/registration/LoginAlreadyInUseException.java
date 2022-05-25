package ru.itis.backend.exceptions.registration;

public class LoginAlreadyInUseException extends RegistrationException {
    public LoginAlreadyInUseException() {
    }

    public LoginAlreadyInUseException(String message) {
        super(message);
    }

    public LoginAlreadyInUseException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginAlreadyInUseException(Throwable cause) {
        super(cause);
    }
}
