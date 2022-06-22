package ru.itis.backend.exceptions.pdf;

public abstract class PdfGenerationException extends RuntimeException {

    public PdfGenerationException() {
        super();
    }

    public PdfGenerationException(String message) {
        super(message);
    }

    public PdfGenerationException(String message, Throwable cause) {
        super(message, cause);
    }

    public PdfGenerationException(Throwable cause) {
        super(cause);
    }
}
