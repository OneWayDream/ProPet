package ru.itis.backend.exceptions.pdf;

public class SelfTreatyException extends PdfGenerationException{

    public SelfTreatyException() {
        super();
    }

    public SelfTreatyException(String message) {
        super(message);
    }

    public SelfTreatyException(String message, Throwable cause) {
        super(message, cause);
    }

    public SelfTreatyException(Throwable cause) {
        super(cause);
    }
}
