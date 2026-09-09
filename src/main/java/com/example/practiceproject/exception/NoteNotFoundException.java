package com.example.practiceproject.exception;

import java.util.UUID;

public class NoteNotFoundException extends BusinessException {
    public NoteNotFoundException(UUID id) {
        super("Note with id: " + id + " not found", 40402);
    }

    public NoteNotFoundException(Throwable cause, int code) {
        super("Database error during note operation", code);
    }

    public NoteNotFoundException(String message, Throwable cause) {
        super(message, 40402);
    }
}
