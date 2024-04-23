package ru.tggc.securityjwt.exception;

public class NoteNotFoundException extends RuntimeException {

    public NoteNotFoundException() {
        super();
    }

    public NoteNotFoundException(String msg) {
        super(msg);
    }
}
