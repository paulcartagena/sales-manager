package com.salesmanager.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Invoice {
    private int invoiceId;
    private int customerId;
    private LocalDateTime invoiceDate;
    private BigDecimal subtotal;
    private BigDecimal tax;
    private BigDecimal total;

    public Invoice () {
    }

    public Invoice(int invoiceId, int customerId, LocalDateTime invoiceDate, BigDecimal subtotal, BigDecimal tax, BigDecimal total) {
        this.invoiceId = invoiceId;
        this.customerId = customerId;
        this.invoiceDate = invoiceDate;
        this.subtotal = subtotal;
        this.tax = tax;
        this.total = total;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public LocalDateTime getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(LocalDateTime invoiceDate) {
        this.invoiceDate = invoiceDate;
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
        return "Id: " + invoiceId +
                ", Customer: " + customerId +
                ", Date: " + invoiceDate +
                ", Subtotal: " + subtotal +
                ", Tax: " + tax +
                ", Total: " + total;
    }
}


