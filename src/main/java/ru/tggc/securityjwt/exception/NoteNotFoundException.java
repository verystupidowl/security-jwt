package ru.tggc.securityjwt.exception;

public class NoteNotFoundException extends RuntimeException {

    public NoteNotFoundException() {
        super("Note not found!");
    }
}
