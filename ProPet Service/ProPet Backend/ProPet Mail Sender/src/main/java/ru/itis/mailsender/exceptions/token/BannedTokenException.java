package ru.itis.mailsender.exceptions.token;

public class BannedTokenException extends TokenAuthenticationException {
    public BannedTokenException() {}

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
