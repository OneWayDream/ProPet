package ru.itis.jwtserver.exceptions.token;

import ru.itis.jwtserver.exceptions.auth.AuthorizationException;

public class BannedTokenException extends AuthorizationException {
    public BannedTokenException() {
    }

    public BannedTokenException(String message) {
        super(message);
    }

    public BannedTokenException(String message, Throwable cause) {
        super(message, cause);
    }

    public BannedTokenException(Throwable cause) {
        super(cause);
    }
}
