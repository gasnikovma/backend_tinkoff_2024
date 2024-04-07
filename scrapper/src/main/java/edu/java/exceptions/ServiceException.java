package edu.java.exceptions;

public class ServiceException extends RuntimeException {
    public int getStatusCode() {
        return statusCode;
    }

    public final int statusCode;

    public ServiceException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}
