package ru.itis.backend.exceptions.pdf;

public class EmptyProfileFieldException extends PdfGenerationException{

    public EmptyProfileFieldException() {
        super();
    }

    public EmptyProfileFieldException(String message) {
        super(message);
    }

    public EmptyProfileFieldException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyProfileFieldException(Throwable cause) {
        super(cause);
    }
}
