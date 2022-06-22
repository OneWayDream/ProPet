package ru.itis.jwtserver.exceptions.token;

import ru.itis.jwtserver.exceptions.auth.AuthorizationException;

public class ExpiredJwtException extends AuthorizationException {
    public ExpiredJwtException() {
    }

    public ExpiredJwtException(String message) {
        super(message);
    }

    public ExpiredJwtException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExpiredJwtException(Throwable cause) {
        super(cause);
    }
}
