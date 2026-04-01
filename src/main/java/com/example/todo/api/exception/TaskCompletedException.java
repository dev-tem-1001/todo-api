package com.example.todo.api.exception;

public class TaskCompletedException extends RuntimeException {
    public TaskCompletedException(String message) {
        super(message);
    }
}
