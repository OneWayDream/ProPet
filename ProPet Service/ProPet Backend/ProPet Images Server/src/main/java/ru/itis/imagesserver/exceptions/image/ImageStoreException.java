package ru.itis.imagesserver.exceptions.image;

public class ImageStoreException extends ImageException{

    public ImageStoreException() {
        super();
    }

    public ImageStoreException(String message) {
        super(message);
    }

    public ImageStoreException(String message, Throwable cause) {
        super(message, cause);
    }

    public ImageStoreException(Throwable cause) {
        super(cause);
    }
}
