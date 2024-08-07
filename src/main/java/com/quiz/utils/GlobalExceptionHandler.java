package com.quiz.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ApiError> handleException(Exception e){
        List<FieldError> fieldErrors = new ArrayList<>();
        ErrorDetails errorDetails = new ErrorDetails(400, e.getMessage(), fieldErrors);
        ApiError apiError = new ApiError(false,e.getMessage(),errorDetails);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }
}
