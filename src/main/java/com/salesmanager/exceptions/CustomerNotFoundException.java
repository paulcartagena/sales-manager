package com.salesmanager.exceptions;

import com.salesmanager.models.Customer;

public class CustomerNotFoundException extends NotFoundException{
    public CustomerNotFoundException(int id) {
        super("Customer not found with id: " + id);
    }
}
