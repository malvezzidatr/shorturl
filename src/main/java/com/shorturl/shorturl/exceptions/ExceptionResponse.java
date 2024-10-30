package com.shorturl.shorturl.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ExceptionResponse {
    
    private String errorCode;
    private String message;

    public ExceptionResponse(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

}
