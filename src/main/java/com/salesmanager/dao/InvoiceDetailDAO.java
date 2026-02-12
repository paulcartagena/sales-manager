package com.salesmanager.dao;

import com.salesmanager.config.ConnectionDB;
import com.salesmanager.models.InvoiceDetail;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InvoiceDetailDAO {

    public List<InvoiceDetail> findByInvoiceId(int invoiceId) {
        List<InvoiceDetail> invoiceDetails = new ArrayList<>();
        String sql = "SELECT * FROM invoice_details WHERE invoice_id = ?";

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, invoiceId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                invoiceDetails.add(mapResultSet(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error fetching details by invoice id");
        }
        return invoiceDetails;
    }

    public InvoiceDetail insert(InvoiceDetail detail) {
        String sql = "INSERT INTO invoice_details (invoice_id, product_id, quantity, unit_price, subtotal) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            return executeInsert(pstmt, detail);
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting invoice details");
        }
    }

    // Transaction overload when creating a sale
    public InvoiceDetail insert(InvoiceDetail detail, Connection conn) throws SQLException {
        String sql = "INSERT INTO invoice_details (invoice_id, product_id, quantity, unit_price, subtotal) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        return executeInsert(pstmt, detail);
    }

    private InvoiceDetail executeInsert(PreparedStatement pstmt, InvoiceDetail detail) throws SQLException {
        pstmt.setInt(1, detail.getInvoice_id());
        pstmt.setInt(2, detail.getProduct_id());
        pstmt.setInt(3, detail.getQuantity());
        pstmt.setBigDecimal(4, detail.getUnit_price());
        pstmt.setBigDecimal(5, detail.getSubtotal());

        int rowsAffected = pstmt.executeUpdate();
        if (rowsAffected == 0) {
            throw new RuntimeException("Insert failed, no rows affected");
        }

        ResultSet rs = pstmt.getGeneratedKeys();
        if (rs.next()) {
            detail.setId_detail(rs.getInt(1));
        }

        return detail;
    }

    public InvoiceDetail mapResultSet(ResultSet rs) throws SQLException {
        InvoiceDetail detail = new InvoiceDetail();
        detail.setId_detail(rs.getInt("id_detail"));
        detail.setInvoice_id(rs.getInt("invoice_id"));
        detail.setProduct_id(rs.getInt("product_id"));
        detail.setQuantity(rs.getInt("quantity"));
        detail.setUnit_price(rs.getBigDecimal("unit_price"));
        detail.setSubtotal(rs.getBigDecimal("subtotal"));
        return detail;
    }
}
