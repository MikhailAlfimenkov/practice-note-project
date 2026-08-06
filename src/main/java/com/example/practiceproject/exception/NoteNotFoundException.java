package com.example.practiceproject.exception;

import java.util.UUID;

public class NoteNotFoundException extends AppException {
    public NoteNotFoundException(UUID id)
    {
        super("Note with id: " + id + " not found", 40402);
    }
}
