package com.shorturl.shorturl.exceptions;

import lombok.Getter;

@Getter
public class UrlNotFoundException extends RuntimeException {
    
    private final String errorCode;
    private final String message;

    public UrlNotFoundException(String errorCode, String message, String details) {
        this.errorCode = errorCode;
        this.message = message;
    }
    
}
