package com.example.relations.exception;

public class UpdateFailException extends RuntimeException{
    public UpdateFailException(String message) {
        super(message);
    }
}
