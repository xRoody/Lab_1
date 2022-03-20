package com.example.lab_1.validationExceptions;

public class UniqueLoginException extends Exception{
    public UniqueLoginException(String message) {
        super(message);
    }
}
