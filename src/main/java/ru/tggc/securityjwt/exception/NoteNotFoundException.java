package ru.tggc.securityjwt.exception;

public class NoteNotFoundException extends RuntimeException {
    private static final String MESSAGE = "Note with id %s not found";

    public NoteNotFoundException(Long id) {
        super(String.format(MESSAGE, id));
    }
}
