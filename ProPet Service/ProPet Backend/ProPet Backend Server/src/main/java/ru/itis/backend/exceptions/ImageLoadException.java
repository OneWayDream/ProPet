package ru.itis.backend.exceptions;

public class ImageLoadException extends ImageException {
    public ImageLoadException() {
    }

    public ImageLoadException(String message) {
        super(message);
    }

    public ImageLoadException(String message, Throwable cause) {
        super(message, cause);
    }

    public ImageLoadException(Throwable cause) {
        super(cause);
    }
}
