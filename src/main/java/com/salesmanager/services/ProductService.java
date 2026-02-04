package com.salesmanager.services;

import com.salesmanager.dao.ProductDAO;
import com.salesmanager.models.Product;

import java.math.BigDecimal;
import java.util.List;

public class ProductService {
    private final ProductDAO productDAO = new ProductDAO();

    // READ
    public List<Product> getAllProducts() {
        return productDAO.findAll();
    }

    public Product getProductById(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid product id");
        }

        Product product = productDAO.findById(id);

        if (product == null) {
            throw new RuntimeException("Product not found with id: " + id);
        }
        return product;
    }

    // BUSINESS
    public void restockProduct(int productId, int quantityToAdd) {
        // 1. Get the product
        Product product = productDAO.findById(productId);

        // 2. Validate
        if (quantityToAdd <= 0 || quantityToAdd > 100) {
            throw new RuntimeException("Error: Quantity must be between 1 and 100");
        }

        // 3. Business logic
        int newStock = product.getStock() + quantityToAdd;

        if (newStock > 200) {
            System.out.println("Warning: Stock exceeds 200 units");
        }

        product.setStock(newStock);
        productDAO.update(product);
    }

    // CREATE
    public Product createProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product is required");
        }
        if (product.getName() == null || product.getName().isBlank()) {
            throw new IllegalArgumentException("Product name is required");
        }
        if (product.getPrice() == null || product.getPrice().signum() <= 0) {
            throw new IllegalArgumentException("Stock cannot be negative");
        }
        if (product.getStock() < 0) {
            throw new IllegalArgumentException("Stock cannot be negative");
        }

        return productDAO.insert(product);
    }

    // DELETE
    public void deleteProduct(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid product id");
        }

        productDAO.delete(id);
    }
}
