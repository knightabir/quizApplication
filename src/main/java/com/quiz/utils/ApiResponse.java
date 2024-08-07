package com.quiz.utils;

import java.time.LocalDateTime;

public class ApiResponse <T>{

    private boolean statue;
    private String message;
    private T data;
    private LocalDateTime dateTime;

    public ApiResponse() {

    }

    public ApiResponse(String message, boolean statue) {
        this.dateTime = LocalDateTime.now();
        this.message = message;
        this.statue = statue;
    }

    public ApiResponse(boolean statue, String message, T data) {
        this.statue = statue;
        this.message = message;
        this.data = data;
        this.dateTime = LocalDateTime.now();
    }

    public ApiResponse(boolean statue, String message, T data, LocalDateTime dateTime) {
        this.statue = statue;
        this.message = message;
        this.data = data;
        this.dateTime = dateTime;
    }

    public boolean isStatue() {
        return statue;
    }

    public void setStatue(boolean statue) {
        this.statue = statue;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
