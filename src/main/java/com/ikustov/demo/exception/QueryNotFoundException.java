package com.ikustov.demo.exception;

public class QueryNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public QueryNotFoundException(String message) {
        super(message);
    }
}
