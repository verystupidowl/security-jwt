package org.tggc.authenticationservice.exception;

public class SendMailException extends RuntimeException {

    public SendMailException(String message, Throwable cause) {
        super(message, cause);
    }
}
