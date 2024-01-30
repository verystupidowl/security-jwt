package ru.tggc.SecurityJWT.exception;

public class NoteNotFoundException extends RuntimeException {

    public NoteNotFoundException() {
        super("Note not found!");
    }
}
