package ru.itis.backend.exceptions.comment;

public class SelfCommentException extends CommentAboutSitterException {
    public SelfCommentException() {
    }

    public SelfCommentException(String message) {
        super(message);
    }

    public SelfCommentException(String message, Throwable cause) {
        super(message, cause);
    }

    public SelfCommentException(Throwable cause) {
        super(cause);
    }
}
