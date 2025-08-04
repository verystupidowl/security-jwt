package ru.tggc.securityjwt.exception;

public class SendMailException extends RuntimeException {

    public SendMailException(String message, Throwable cause) {
        super(message, cause);
    }
}
