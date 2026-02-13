package com.salesmanager.exceptions;

public class InvalidIdException extends RuntimeException{
    public InvalidIdException(String entity) {
        super("Invalid id for: " + entity);
    }
}
