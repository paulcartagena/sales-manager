package com.salesmanager.dao;

import com.salesmanager.config.ConnectionDB;
import com.salesmanager.models.Invoice;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InvoiceDAO {

    public List<Invoice> findAll() {
        List<Invoice> invoices = new ArrayList<>();
        String sql = "SELECT * FROM invoices ORDER BY id_invoice";

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                invoices.add(mapResultSet(rs));
            }

        } catch (SQLException e) {
           throw new RuntimeException("Error fetching all invoices", e);
        }
        return invoices;
    }

    public Invoice findById(int id) {
        Invoice invoice = null;
        String sql = "SELECT * FROM invoices WHERE id_invoice = ?";

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                invoice = mapResultSet(rs);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error fetching invoice by id");
        }
        return invoice;
    }

    public Invoice insert(Invoice invoice) {
        String sql = "INSERT INTO invoices (customer_id, invoice_date, subtotal, tax, total) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, invoice.getCustomer_id());
            pstmt.setTimestamp(2, Timestamp.valueOf(invoice.getInvoice_date()));
            pstmt.setBigDecimal(3, invoice.getSubtotal());
            pstmt.setBigDecimal(4, invoice.getTax());
            pstmt.setBigDecimal(5, invoice.getTotal());

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected == 0) {
                throw new RuntimeException("Insert failed, no rows affected");
            }

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                invoice.setId_invoice(rs.getInt(1));
            }

            return invoice;
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting invoice", e);
        }
    }

    public Invoice mapResultSet(ResultSet rs) throws SQLException {
        Invoice invoice = new Invoice();
        invoice.setId_invoice(rs.getInt("id_invoice"));
        invoice.setCustomer_id(rs.getInt("customer_id"));
        invoice.setInvoice_date(rs.getTimestamp("invoice_date").toLocalDateTime());
        invoice.setSubtotal(rs.getBigDecimal("subtotal"));
        invoice.setTax(rs.getBigDecimal("tax"));
        invoice.setTotal(rs.getBigDecimal("total"));
        return invoice;
    }
}
