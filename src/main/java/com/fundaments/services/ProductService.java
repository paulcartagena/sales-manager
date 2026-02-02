package com.fundaments.services;

import com.fundaments.dao.ProductDAO;
import com.fundaments.models.Product;

public class ProductService {
    private ProductDAO productDAO = new ProductDAO();

    public void restockProduct(int productId, int quantityToAdd) {
        // 1. Get the product
        Product product = productDAO.findById(productId);

        // 2. Validate
        if (product == null) {
            throw new RuntimeException("Product not found");
        }
        if (quantityToAdd <= 0 || quantityToAdd > 100) {
            throw new RuntimeException("Error: Quantity must be between 1 and 100");
        }

        // 3. Business logic
        int newStock = product.getStock() + quantityToAdd;

        if (newStock > 200) {
            System.out.println("Warning: Stock exceeds 200 units");
        }

        // 4. Update BD
        product.setStock(newStock);
        Product updatedProduct = productDAO.update(product);

        if (updatedProduct == null) {
            throw new RuntimeException("Error updating stock");
        }
    }
}
