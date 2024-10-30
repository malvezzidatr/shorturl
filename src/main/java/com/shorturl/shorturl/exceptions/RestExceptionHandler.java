package com.shorturl.shorturl.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UrlNotFoundException.class)
    public ResponseEntity<Object> handleUrlNotFoundException(UrlNotFoundException ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse("URL_NOT_FOUND", ex.getMessage());
        return ResponseEntity.badRequest().body(exceptionResponse);
    }

    @ExceptionHandler(ExtractDomainException.class)
    public ResponseEntity<Object> handleExtractDomainException(ExtractDomainException ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getErrorCode(), ex.getMessage());
        return ResponseEntity.badRequest().body(exceptionResponse);
    }

    @ExceptionHandler(AliasAlreadyUsedException.class)
    public ResponseEntity<Object> handleAliasAlreadyUsedException(AliasAlreadyUsedException ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse("ALIAS_ALREADY_USED", "Alias already in use");
        return ResponseEntity.badRequest().body(exceptionResponse);
    }
}
