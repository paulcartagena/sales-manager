package com.fundaments.dao;

import com.fundaments.config.ConnectionDB;
import com.fundaments.models.InvoiceDetail;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InvoiceDetailDAO {
    // READ
    public List<InvoiceDetail> findByInvoiceId(int invoiceId) {
        List<InvoiceDetail> invoiceDetails = new ArrayList<>();
        String sql = "SELECT * FROM invoice_details WHERE invoice_id = ?";

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, invoiceId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                InvoiceDetail detail = new InvoiceDetail();
                detail.setId_detail(rs.getInt("id_detail"));
                detail.setInvoice_id(rs.getInt("invoice_id"));
                detail.setProduct_id(rs.getInt("product_id"));
                detail.setQuantity(rs.getInt("quantity"));
                detail.setUnit_price(rs.getBigDecimal("unit_price"));
                detail.setSubtotal(rs.getBigDecimal("subtotal"));

                invoiceDetails.add(detail);
            }

        } catch (SQLException e) {
            System.out.println("Error al encontrar la lista de detalle de la factura: " + e.getMessage());
        }
        return invoiceDetails;
    }

    public InvoiceDetail insert(InvoiceDetail detail) {
        String sql = "INSERT INTO invoice_details (invoice_id, product_id, quantity, unit_price, subtotal) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, detail.getInvoice_id());
            pstmt.setInt(2, detail.getProduct_id());
            pstmt.setInt(3, detail.getQuantity());
            pstmt.setBigDecimal(4, detail.getUnit_price());
            pstmt.setBigDecimal(5, detail.getSubtotal());

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    detail.setId_detail(rs.getInt(1));
                }
                return detail;
            }
        } catch (SQLException e) {
            System.err.println("Error al insertar la factura: " + e.getMessage());
        }
        return null;
    }
}
