package com.salesmanager.services;

import com.salesmanager.dao.ProductDAO;
import com.salesmanager.exceptions.InvalidIdException;
import com.salesmanager.exceptions.ProductNotFoundException;
import com.salesmanager.models.Product;

import java.util.List;

public class ProductService {
    private final ProductDAO productDAO = new ProductDAO();

    // READ
    public List<Product> getAllProducts() {
        return productDAO.findAll();
    }

    public Product getProductById(int id) {
        if (id <= 0) {
            throw new InvalidIdException("product");
        }

        Product product = productDAO.findById(id);

        if (product == null) {
            throw new ProductNotFoundException(id);
        }

        return product;
    }

    // BUSINESS
    public void restockProduct(int productId, int quantityToAdd) {
        // 1. Get the product
        Product product = productDAO.findById(productId);

        // 2. Validate
        if (product == null) {
            throw new ProductNotFoundException(productId);
        }

        if (quantityToAdd <= 0 || quantityToAdd > 100) {
            throw new IllegalArgumentException("Quantity must be between 1 and 100");
        }

        // 3. Business logic
        int newStock = product.getStock() + quantityToAdd;

        if (newStock > 200) {
            throw new IllegalArgumentException("Stock would exceed maximum allowed (200 units)");
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
            throw new IllegalArgumentException("Price must be greater than 0");
        }
        if (product.getStock() < 0) {
            throw new IllegalArgumentException("Stock cannot be negative");
        }

        return productDAO.insert(product);
    }

    // DELETE
    public void deleteProduct(int id) {
        if (id <= 0) {
            throw new InvalidIdException("product");
        }

        Product product = productDAO.findById(id);

        if (product == null) {
            throw new ProductNotFoundException(id);
        }

        productDAO.delete(id);
    }
}
