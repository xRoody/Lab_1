package com.example.lab_1.schedulerExceptions;

public class JobCreateException extends Exception{
    public JobCreateException() {
    }

    public JobCreateException(String message) {
        super(message);
    }

    public JobCreateException(String message, Throwable cause) {
        super(message, cause);
    }
}
