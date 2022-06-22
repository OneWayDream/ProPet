package ru.itis.backend.exceptions.pdf;

public class UnconfirmedApplyException extends PdfGenerationException{

    public UnconfirmedApplyException() {
        super();
    }

    public UnconfirmedApplyException(String message) {
        super(message);
    }

    public UnconfirmedApplyException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnconfirmedApplyException(Throwable cause) {
        super(cause);
    }
}
