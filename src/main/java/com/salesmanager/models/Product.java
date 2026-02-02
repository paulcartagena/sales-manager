package com.salesmanager.models;

import java.math.BigDecimal;

public class Product {
    private int id_product;
    private String name;
    private BigDecimal price;
    private int stock;

    public Product() {
    }

    public Product(int id_product, String name, BigDecimal price, int stock){
        this.id_product = id_product;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public int getId_product() {
        return id_product;
    }

    public void setId_product(int id_product) {
        this.id_product = id_product;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    // Business Methods
    public boolean isAvailable() {
        return this.stock > 0;
    }

    public BigDecimal calculateValueInventory() {
        return price.multiply(BigDecimal.valueOf(stock));
    }

    @Override
    public String toString() {
        return "Product: " + id_product +
                ", Name: " + name +
                ", Price: $" + price +
                ", Stock: " + stock;
    }
}