package com.salesmanager.services;

import com.salesmanager.dao.CustomerDAO;
import com.salesmanager.exceptions.CustomerNotFoundException;
import com.salesmanager.exceptions.InvalidIdException;
import com.salesmanager.models.Customer;

import java.util.List;

public class CustomerService {
    private final CustomerDAO customerDAO = new CustomerDAO();

    // READ
    public List<Customer> getAllCustomers() {
        return customerDAO.findAll();
    }

    public Customer getCustomerById(int id) {
        if (id <= 0) {
            throw new InvalidIdException("customer");
        }

        Customer customer = customerDAO.findById(id);

        if (customer == null) {
            throw new CustomerNotFoundException(id);
        }

        return customer;
    }

    // INSERT
    public Customer createCustomer(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer is required");
        }
        if (customer.getName() == null || customer.getName().isBlank()) {
            throw new IllegalArgumentException("Customer name is required");
        }
        if (customer.getEmail() == null || customer.getEmail().isBlank()) {
            throw new IllegalArgumentException("Customer email is required");
        }
        if (customer.getPhone() == null || customer.getPhone().isBlank()) {
            throw new IllegalArgumentException("Customer phone is required");
        }

        // Business rules
        if (!customer.getEmail().contains("@")) {
            throw new IllegalArgumentException("Invalid email format");
        }

        return customerDAO.insert(customer);
    }

    // UPDATE
    public Customer updateCustomer(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer is required");
        }
        if (customer.getCustomerId() <= 0) {
            throw new InvalidIdException("customer");
        }
        if (customer.getName() == null || customer.getName().isBlank()) {
            throw new IllegalArgumentException("Customer name is required");
        }
        if (customer.getEmail() == null || customer.getEmail().isBlank()) {
            throw new IllegalArgumentException("Customer email is required");
        }
        if (customer.getPhone() == null || customer.getPhone().isBlank()) {
            throw new IllegalArgumentException("Customer phone is required");
        }

        Customer existing = customerDAO.findById(customer.getCustomerId());

        if (existing == null) {
            throw new CustomerNotFoundException(customer.getCustomerId());
        }

        // Business rules
        if (!customer.getEmail().contains("@")) {
            throw new IllegalArgumentException("Invalid email format");
        }

        return customerDAO.update(customer);
    }

    // DELETE
    public void deleteCustomer(int id) {
        if (id <= 0) {
            throw new InvalidIdException("customer");
        }

        Customer existing = customerDAO.findById(id);

        if (existing == null) {
            throw new CustomerNotFoundException(id);
        }

        customerDAO.delete(id);
    }
}
