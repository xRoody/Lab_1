package com.example.lab_1.validationExceptions;

public class UniqueNickNameException extends Exception{
    public UniqueNickNameException(String message) {
        super(message);
    }
}
