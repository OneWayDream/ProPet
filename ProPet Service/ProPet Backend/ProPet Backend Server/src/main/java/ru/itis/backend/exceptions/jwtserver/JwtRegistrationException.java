package ru.itis.backend.exceptions.jwtserver;

public class JwtRegistrationException extends JwtServerException {
    public JwtRegistrationException() {
    }

    public JwtRegistrationException(String message) {
        super(message);
    }

    public JwtRegistrationException(String message, Throwable cause) {
        super(message, cause);
    }

    public JwtRegistrationException(Throwable cause) {
        super(cause);
    }
}
