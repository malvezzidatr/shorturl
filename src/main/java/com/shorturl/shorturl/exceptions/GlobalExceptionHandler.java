package com.shorturl.shorturl.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UrlNotFoundException.class)
    public ResponseEntity<Object> handleUrlNotFoundException(UrlNotFoundException ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse("URL_NOT_FOUND", ex.getMessage());
        return ResponseEntity.badRequest().body(exceptionResponse);
    }
}
