package com.example.practiceproject.exception;

import java.util.UUID;

public class AuthorNotFound extends BusinessException {
    public AuthorNotFound(UUID id) {
        super("Author with id: " + id + " not found", 40401);
    }
}
