package com.salesmanager.exceptions;

public class InvoiceNotFoundException extends NotFoundException{
    public InvoiceNotFoundException(int id) {
        super("Invoice not Found with id: " + id);
    }
}
