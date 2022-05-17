package ru.itis.backend.exceptions.comment;

public abstract class CommentAboutSitterException extends RuntimeException{

    public CommentAboutSitterException() {
    }

    public CommentAboutSitterException(String message) {
        super(message);
    }

    public CommentAboutSitterException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommentAboutSitterException(Throwable cause) {
        super(cause);
    }
}
