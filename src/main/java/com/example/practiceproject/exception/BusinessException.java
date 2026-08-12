package com.example.practiceproject.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusinessException extends RuntimeException {
    private final int errorCode;

    public BusinessException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
