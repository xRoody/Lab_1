package com.example.lab_1.schedulerExceptions;

public class JobUpdateException extends Exception{
    public JobUpdateException() {
    }

    public JobUpdateException(String message) {
        super(message);
    }

    public JobUpdateException(String message, Throwable cause) {
        super(message, cause);
    }
}
