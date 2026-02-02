package com.salesmanager.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Invoice {
    private int id_invoice;
    private int customer_id;
    private LocalDateTime invoice_date;
    private BigDecimal subtotal;
    private BigDecimal tax;
    private BigDecimal total;

    public Invoice () {
    }

    public Invoice(int id_invoice, int customer_id, LocalDateTime invoice_date, BigDecimal subtotal, BigDecimal tax, BigDecimal total) {
        this.id_invoice = id_invoice;
        this.customer_id = customer_id;
        this.invoice_date = invoice_date;
        this.subtotal = subtotal;
        this.tax = tax;
        this.total = total;
    }

    public int getId_invoice() {
        return id_invoice;
    }

    public void setId_invoice(int id_invoice) {
        this.id_invoice = id_invoice;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public LocalDateTime getInvoice_date() {
        return invoice_date;
    }

    public void setInvoice_date(LocalDateTime invoice_date) {
        this.invoice_date = invoice_date;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Id: " + id_invoice +
                ", Customer: " + customer_id +
                ", Date: " + invoice_date +
                ", Subtotal: " + subtotal +
                ", Tax: " + tax +
                ", Total: " + total;
    }
}


