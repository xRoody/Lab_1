package com.example.lab_1.validationExceptions;

public class UniqueEmailException extends Exception{
    public UniqueEmailException(String message) {
        super(message);
    }
}
