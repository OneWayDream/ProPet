package ru.itis.pdfgenerator.exceptions.store;

public class PdfLoadException extends RuntimeException{

    public PdfLoadException() {
        super();
    }

    public PdfLoadException(String message) {
        super(message);
    }

    public PdfLoadException(String message, Throwable cause) {
        super(message, cause);
    }

    public PdfLoadException(Throwable cause) {
        super(cause);
    }
}
