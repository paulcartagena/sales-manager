package com.salesmanager.dao;

import com.salesmanager.config.ConnectionDB;
import com.salesmanager.models.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products ORDER BY id_product";

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                products.add(mapResultSet(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error fetching all products", e);
        }
        return products;
    }

    public Product findById(int id) {
        Product product = null;
        String sql = "SELECT * FROM products WHERE id_product = ?";

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                product = mapResultSet(rs);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error fetching product by id", e);
        }
        return product;
    }

    public Product insert(Product product) {
        String sql = "INSERT INTO products (name, price, stock) VALUES (?, ?, ?)";

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, product.getName());
            pstmt.setBigDecimal(2, product.getPrice());
            pstmt.setInt(3, product.getStock());

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected == 0) {
                throw new RuntimeException("Insert failed, no rows affected");
            }

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                product.setId_product(rs.getInt(1));
            }

            return product;
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting product", e);
        }
    }

    public Product update(Product product) {
        String sql = "UPDATE products SET name = ?, price = ?, stock = ? WHERE id_product = ?";

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            return executeUpdate(pstmt, product);
        } catch (SQLException e) {
            throw new RuntimeException("Error updating product", e);
        }
    }

    // Transaction overload when creating a sale
    public Product update(Product product, Connection conn) throws SQLException {
        String sql = "UPDATE products SET name = ?, price = ?, stock = ? WHERE id_product = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);

        return executeUpdate(pstmt, product);
    }

    private Product executeUpdate(PreparedStatement pstmt, Product product) throws SQLException {
        pstmt.setString(1, product.getName());
        pstmt.setBigDecimal(2, product.getPrice());
        pstmt.setInt(3, product.getStock());
        pstmt.setInt(4, product.getId_product());

        int rowsAffected = pstmt.executeUpdate();
        if (rowsAffected == 0) {
            throw new RuntimeException("Update failed, no rows affected");
        }

        return product;
    }

    public void delete(int id) {
        String sql = "DELETE FROM products WHERE id_product = ?";

        try (Connection conn = ConnectionDB.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected == 0) {
                throw new RuntimeException("Delete failed, no rows affected");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error deleting product", e);
        }
    }

    private Product mapResultSet(ResultSet rs) throws SQLException {
        Product product = new Product();
        product.setId_product(rs.getInt("id_product"));
        product.setName(rs.getString("name"));
        product.setPrice(rs.getBigDecimal("price"));
        product.setStock(rs.getInt("stock"));
        return product;
    }
}
