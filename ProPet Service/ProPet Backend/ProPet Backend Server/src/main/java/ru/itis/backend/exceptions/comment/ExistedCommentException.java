package ru.itis.backend.exceptions.comment;

public class ExistedCommentException extends CommentAboutSitterException {
    public ExistedCommentException() {
    }

    public ExistedCommentException(String message) {
        super(message);
    }

    public ExistedCommentException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExistedCommentException(Throwable cause) {
        super(cause);
    }
}
