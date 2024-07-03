package com.gwangju3.bookforest.exception;

import com.gwangju3.bookforest.dto.MessageResponse;
import com.gwangju3.bookforest.exception.global.UnauthorizedDeletionException;
import com.gwangju3.bookforest.exception.global.UnauthorizedModificationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UnauthorizedModificationException.class)
    public ResponseEntity<MessageResponse> handleUnauthorizedModificationException(UnauthorizedModificationException e) {
        MessageResponse messageResponse = new MessageResponse(e.getMessage());
        return new ResponseEntity<>(messageResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UnauthorizedDeletionException.class)
    public ResponseEntity<MessageResponse> handleUnauthorizedDeletionException(UnauthorizedDeletionException e) {
        MessageResponse messageResponse = new MessageResponse(e.getMessage());
        return new ResponseEntity<>(messageResponse, HttpStatus.UNAUTHORIZED);
    }
}
