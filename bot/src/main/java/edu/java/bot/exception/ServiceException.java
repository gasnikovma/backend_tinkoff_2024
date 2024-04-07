package edu.java.bot.exception;

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
