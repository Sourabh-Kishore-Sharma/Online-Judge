package com.algojudge.algojudge.exception;


import com.algojudge.algojudge.entity.ErrorDetail;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

// This annotation makes this class capable of handling exceptions across the whole application
@ControllerAdvice
public class GlobalExceptionalHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetail> handleResourceNotFoundException(
            ResourceNotFoundException ex, WebRequest request) {
        ErrorDetail errorDetail = new ErrorDetail(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorDetail, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorDetail> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request){
        ErrorDetail errorDetail = new ErrorDetail(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorDetail, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetail> handleGlobalException(Exception ex, WebRequest request){
        ErrorDetail errorDetail = new ErrorDetail(
                LocalDateTime.now(),
                "An unexpected error occurred. Please try again later.",
                request.getDescription(false) + " - Debug Info: "+ex.getMessage()
        );
        ex.printStackTrace();
        return new ResponseEntity<>(errorDetail, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

