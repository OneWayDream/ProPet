package ru.itis.backend.exceptions.registration;

public class MailAlreadyInUseException extends RegistrationException {
    public MailAlreadyInUseException() {
    }

    public MailAlreadyInUseException(String message) {
        super(message);
    }

    public MailAlreadyInUseException(String message, Throwable cause) {
        super(message, cause);
    }

    public MailAlreadyInUseException(Throwable cause) {
        super(cause);
    }
}
