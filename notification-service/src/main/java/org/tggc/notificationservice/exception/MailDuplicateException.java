package org.tggc.notificationservice.exception;

public class MailDuplicateException extends RuntimeException {
    
    public MailDuplicateException(String message) {
        super(message);
    }
}
