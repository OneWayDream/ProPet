package ru.itis.backend.exceptions.pdf;

public class NoTreatyInfoException extends PdfGenerationException{

    public NoTreatyInfoException() {
        super();
    }

    public NoTreatyInfoException(String message) {
        super(message);
    }

    public NoTreatyInfoException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoTreatyInfoException(Throwable cause) {
        super(cause);
    }
}
