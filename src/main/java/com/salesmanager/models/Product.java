package com.salesmanager.models;

import java.math.BigDecimal;

public class Product {
    private int productId;
    private String name;
    private BigDecimal price;
    private int stock;

    public Product() {
    }

    public Product(int productId, String name, BigDecimal price, int stock){
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public Product(String name, BigDecimal price, int stock){
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
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
        return "Product: " + productId +
                ", Name: " + name +
                ", Price: $" + price +
                ", Stock: " + stock;
    }
}