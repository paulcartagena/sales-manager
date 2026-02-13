package com.salesmanager.exceptions;

public class InsufficientStockException extends RuntimeException{
    public InsufficientStockException(int id) {
        super("Insufficient stock for product id: " + id);
    }
}
