package org.tggc.notificationservice.exception;

public class SendMailException extends RuntimeException {

    public SendMailException(String message, Throwable cause) {
        super(message, cause);
    }
}
