package ru.itis.backend.exceptions.jwtserver;

public class JwtUpdateException extends JwtServerException {
    public JwtUpdateException() {
    }

    public JwtUpdateException(String message) {
        super(message);
    }

    public JwtUpdateException(String message, Throwable cause) {
        super(message, cause);
    }

    public JwtUpdateException(Throwable cause) {
        super(cause);
    }
}
