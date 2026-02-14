package com.salesmanager.services;

import com.salesmanager.config.ConnectionDB;
import com.salesmanager.dao.CustomerDAO;
import com.salesmanager.dao.InvoiceDAO;
import com.salesmanager.dao.InvoiceDetailDAO;
import com.salesmanager.dao.ProductDAO;
import com.salesmanager.exceptions.*;
import com.salesmanager.models.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class InvoiceService {
    private static final BigDecimal TAX_RATE = new BigDecimal("0.13");

    private final InvoiceDAO invoiceDAO = new InvoiceDAO();
    private final CustomerDAO customerDAO = new CustomerDAO();
    private final ProductDAO productDAO = new ProductDAO();
    private final InvoiceDetailDAO invoiceDetailDAO = new InvoiceDetailDAO();

    // READ
    public List<Invoice> getAllInvoices() {
        return invoiceDAO.findAll();
    }

    public Invoice getInvoiceById(int id) {
        if (id <= 0) {
            throw new InvalidIdException("invoice");
        }

        Invoice invoice = invoiceDAO.findById(id);

        if (invoice == null) {
            throw new InvoiceNotFoundException(id);
        }

        return invoice;
    }

    public List<InvoiceDetail> getDtByInvoiceId(int invoiceId) {
        if (invoiceId <= 0) {
            throw new InvalidIdException("invoice");
        }

        List<InvoiceDetail> details = invoiceDetailDAO.findByInvoiceId(invoiceId);

        if (details == null || details.isEmpty()) {
            throw new InvoiceNotFoundException(invoiceId);
        }

        return details;
    }

    // BUSINESS
    public Invoice createSale(int customerId, List<SaleItem> items) {
        // 1, Validate customer
        Customer customer = customerDAO.findById(customerId);

        if (customer == null) {
            throw new CustomerNotFoundException(customerId);
        }

        // 2. Validate products and calculate total
        List<Product> products = new ArrayList<>();
        BigDecimal subtotal = BigDecimal.ZERO;

        for (SaleItem item : items) {
            Product product = productDAO.findById(item.getProductId());

            if (product == null) {
                throw new ProductNotFoundException(item.getProductId());
            }
            if (product.getStock() < item.getQuantity()) {
                throw new InsufficientStockException(item.getProductId());
            }

            BigDecimal itemSubtotal =
                    product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
            subtotal = subtotal.add(itemSubtotal);
            products.add(product);
        }

        BigDecimal tax = subtotal.multiply(TAX_RATE);
        BigDecimal total = subtotal.add(tax);

        // 3. Sale transaction
        Connection conn = null;
        try {
            conn = ConnectionDB.getConnection();
            conn.setAutoCommit(false);

            // 3.1 Save invoice
            Invoice invoice = new Invoice();
            invoice.setCustomerId(customerId);
            invoice.setInvoiceDate(LocalDateTime.now());
            invoice.setSubtotal(subtotal);
            invoice.setTax(tax);
            invoice.setTotal(total);

            Invoice savedInvoice = invoiceDAO.insert(invoice, conn);

            // 3.2 Save invoice details
            for (int i = 0; i < items.size(); i++) {
                SaleItem item = items.get(i);
                Product product = products.get(i);

                BigDecimal itemSubtotal =
                        product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));

                InvoiceDetail detail = new InvoiceDetail();
                detail.setInvoiceId(savedInvoice.getInvoiceId());
                detail.setProductId(item.getProductId());
                detail.setQuantity(item.getQuantity());
                detail.setUnitPrice(product.getPrice());
                detail.setSubtotal(itemSubtotal);

                invoiceDetailDAO.insert(detail, conn);
            }

            // 3.3 Update stock
            for (int i = 0; i < items.size(); i++) {
                SaleItem item = items.get(i);
                Product product = products.get(i);

                product.setStock(product.getStock() - item.getQuantity());

                productDAO.update(product, conn);
            }

            conn.commit();
            return savedInvoice;
        } catch (RuntimeException e) {
            rollback(conn);
            throw e;
        } catch (SQLException e) {
            rollback(conn);
            throw new RuntimeException("Database error during sale creation", e);
        } finally {
            closeConnection(conn);
        }
    }

    private void rollback(Connection conn) {
        if (conn != null) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException("Error during rollback", ex);
            }
        }
    }

    private void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.setAutoCommit(true);
                conn.close();
            } catch (SQLException ex) {
                throw new RuntimeException("Error closing connection", ex);
            }
        }
    }
}
