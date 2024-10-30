package com.shorturl.shorturl.exceptions;

import lombok.Getter;

@Getter
public class AliasAlreadyUsedException extends RuntimeException {
    
    private final String errorCode;
    private final String message;

    public AliasAlreadyUsedException(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

}
