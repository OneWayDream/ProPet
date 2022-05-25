package ru.itis.backend.exceptions.jwtserver;

public class JwtAuthorizationFaultException extends JwtServerException{

    public JwtAuthorizationFaultException() {
        super();
    }

    public JwtAuthorizationFaultException(String message) {
        super(message);
    }

    public JwtAuthorizationFaultException(String message, Throwable cause) {
        super(message, cause);
    }

    public JwtAuthorizationFaultException(Throwable cause) {
        super(cause);
    }
}
