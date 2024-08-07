package com.quiz.utils;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class FieldError {
    private String field;
    private String message;
}
