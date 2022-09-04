package ru.itis.pdfgenerator.exceptions.store;

public class PdfStoreException extends RuntimeException{

    public PdfStoreException() {
        super();
    }

    public PdfStoreException(String message) {
        super(message);
    }

    public PdfStoreException(String message, Throwable cause) {
        super(message, cause);
    }

    public PdfStoreException(Throwable cause) {
        super(cause);
    }
}
