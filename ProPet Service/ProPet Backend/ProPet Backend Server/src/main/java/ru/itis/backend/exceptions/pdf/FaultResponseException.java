package ru.itis.backend.exceptions.pdf;

public class FaultResponseException extends PdfGenerationException{

    public FaultResponseException() {
        super();
    }

    public FaultResponseException(String message) {
        super(message);
    }

    public FaultResponseException(String message, Throwable cause) {
        super(message, cause);
    }

    public FaultResponseException(Throwable cause) {
        super(cause);
    }
}
