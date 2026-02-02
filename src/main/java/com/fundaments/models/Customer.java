package com.fundaments.models;

public class Customer {
    private int id_customer;
    private String name;
    private String email;
    private String phone;

    public Customer() {
    }

    public Customer(int id_customer, String name, String email, String phone) {
        this.id_customer = id_customer;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public Customer(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public int getId_customer() {
        return id_customer;
    }

    public void setId_customer(int id_customer) {
        this.id_customer = id_customer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Customer: " + id_customer +
                ", Name: " + name +
                ", Email: " + email +
                ", Phone: " + phone;
    }
}
