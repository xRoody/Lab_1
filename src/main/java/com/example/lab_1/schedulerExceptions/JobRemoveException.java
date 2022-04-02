package com.example.lab_1.schedulerExceptions;

public class JobRemoveException extends Exception{
    public JobRemoveException() {
    }

    public JobRemoveException(String message) {
        super(message);
    }

    public JobRemoveException(String message, Throwable cause) {
        super(message, cause);
    }
}
