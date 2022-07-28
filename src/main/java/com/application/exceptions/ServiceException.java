package com.application.exceptions;

import java.util.Map;

public class ServiceException extends Exception{
    private Map<String, Object> errors;

    public ServiceException() {
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Map<String, Object> errors) {
        this.errors = errors;
    }

    public Map<String, Object> getErrors() {
        return errors;
    }
}
