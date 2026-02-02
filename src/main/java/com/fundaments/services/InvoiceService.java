package com.fundaments.services;

import com.fundaments.dao.CustomerDAO;
import com.fundaments.dao.InvoiceDAO;
import com.fundaments.dao.InvoiceDetailDAO;
import com.fundaments.dao.ProductDAO;
import com.fundaments.models.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class InvoiceService {

    private static final BigDecimal TAX_RATE = new BigDecimal("0.13");

    private InvoiceDAO invoiceDAO = new InvoiceDAO();
    private CustomerDAO customerDAO = new CustomerDAO();
    private ProductDAO productDAO = new ProductDAO();
    private InvoiceDetailDAO invoiceDetailDAO = new InvoiceDetailDAO();

    public Invoice createSale(int customerId, List<SaleItem> items) {
        // 1, Validate customer
        Customer customer = customerDAO.findById(customerId);
        if (customer == null) {
            throw new RuntimeException("Customer not found");
        }

        // 2. Validate products and calculate total
        List<Product> products = new ArrayList<>();
        BigDecimal subtotal = BigDecimal.ZERO;

        for (SaleItem item : items) {
            Product product = productDAO.findById(item.getProduct_id());
            if (product == null) {
                throw new RuntimeException("Product not found: " + item.getProduct_id());
            }
            if (product.getStock() < item.getQuantity()) {
                throw new RuntimeException("Insufficient stock: " + item.getProduct_id());
            }

            BigDecimal itemSubtotal =
                    product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
            subtotal = subtotal.add(itemSubtotal);
            products.add(product);
        }

        BigDecimal tax = subtotal.multiply(TAX_RATE);
        BigDecimal total = subtotal.add(tax);

        // 3. Save invoice
        Invoice invoice = new Invoice();
        invoice.setCustomer_id(customerId);
        invoice.setInvoice_date(LocalDateTime.now());
        invoice.setSubtotal(subtotal);
        invoice.setTax(tax);
        invoice.setTotal(total);

        Invoice savedInvoice = invoiceDAO.insert(invoice);
        if (savedInvoice == null) {
            throw new RuntimeException("Error saving invoice");
        }

        // 4. Save invoice details
        for (int i = 0; i < items.size(); i++) {
            SaleItem item = items.get(i);
            Product product = products.get(i);

            BigDecimal itemSubtotal =
                    product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));

            InvoiceDetail detail = new InvoiceDetail();
            detail.setInvoice_id(savedInvoice.getId_invoice());
            detail.setProduct_id(item.getProduct_id());
            detail.setQuantity(item.getQuantity());
            detail.setUnit_price(product.getPrice());
            detail.setSubtotal(itemSubtotal);

            InvoiceDetail savedDetail = invoiceDetailDAO.insert(detail);
            if (savedDetail == null) {
                throw new RuntimeException("Error saving detail");
            }
        }

        // 5. Update stock
        for (int i = 0; i < items.size(); i++) {
            SaleItem item = items.get(i);
            Product product = products.get(i);

            product.setStock(product.getStock() - item.getQuantity());
            Product updatedProduct = productDAO.update(product);
            if (updatedProduct == null) {
                throw new RuntimeException("Error updating product");
            }
        }

        System.out.println("Invoice #" + savedInvoice.getId_invoice());
        System.out.println("Items:  " + items.size());
        System.out.println("Total: " + total);

        return savedInvoice;
    }
}
