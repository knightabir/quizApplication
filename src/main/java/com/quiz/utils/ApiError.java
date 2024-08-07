package com.quiz.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiError {
    private boolean status;
    private String message;
    private ErrorDetails error;
    private LocalDateTime timestamp;

    public ApiError(boolean status, String message,ErrorDetails error) {
        this.error = error;
        this.message = message;
        this.status = status;
    }
}
