package com.salesmanager.dao;

import com.salesmanager.config.ConnectionDB;
import com.salesmanager.models.Invoice;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InvoiceDAO {
    // READ
    public List<Invoice> findAll() {
        List<Invoice> invoices = new ArrayList<>();
        String sql = "SELECT * FROM invoices ORDER BY id_invoice";

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Invoice invoice = new Invoice();
                invoice.setId_invoice(rs.getInt("id_invoice"));
                invoice.setCustomer_id(rs.getInt("customer_id"));
                invoice.setInvoice_date(rs.getTimestamp("invoice_date").toLocalDateTime());
                invoice.setSubtotal(rs.getBigDecimal("subtotal"));
                invoice.setTax(rs.getBigDecimal("tax"));
                invoice.setTotal(rs.getBigDecimal("total"));

                invoices.add(invoice);
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener facturas: " + e.getMessage());
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
                invoice = new Invoice();
                invoice.setId_invoice(rs.getInt("id_invoice"));
                invoice.setCustomer_id(rs.getInt("customer_id"));
                invoice.setInvoice_date(rs.getTimestamp("invoice_date").toLocalDateTime());
                invoice.setSubtotal(rs.getBigDecimal("subtotal"));
                invoice.setTax(rs.getBigDecimal("tax"));
                invoice.setTotal(rs.getBigDecimal("total"));
            }
        } catch (SQLException e) {
            System.err.println("Error al encontrar la factura por id: " + e.getMessage());
        }
        return invoice;
    }

    //INSERT
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

            if (rowsAffected > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    invoice.setId_invoice(rs.getInt(1));
                }
                return invoice;
            }

        } catch (SQLException e) {
            System.err.println("Error al insertar factura: " + e.getMessage());
        }
        return null;
    }
}
