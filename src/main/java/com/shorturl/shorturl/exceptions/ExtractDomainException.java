package com.shorturl.shorturl.exceptions;

import lombok.Getter;

@Getter
public class ExtractDomainException extends RuntimeException {

    private final String errorCode;
    private final String message;

    public ExtractDomainException(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

}
