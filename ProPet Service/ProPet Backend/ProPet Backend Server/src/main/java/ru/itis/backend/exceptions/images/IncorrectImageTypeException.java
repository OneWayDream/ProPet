package ru.itis.backend.exceptions.images;

public class IncorrectImageTypeException extends ImageException{

    public IncorrectImageTypeException() {
        super();
    }

    public IncorrectImageTypeException(String message) {
        super(message);
    }

    public IncorrectImageTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public IncorrectImageTypeException(Throwable cause) {
        super(cause);
    }
}
