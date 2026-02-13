package com.salesmanager.exceptions;

public class ProductNotFoundException extends NotFoundException{
    public ProductNotFoundException(int id) {
        super("Product not found with id: " + id);
    }
}
