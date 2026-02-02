package com.salesmanager.models;

import java.math.BigDecimal;

public class InvoiceDetail {
    private int id_detail;
    private int invoice_id;
    private int product_id;
    private int quantity;
    private BigDecimal unit_price;
    private BigDecimal subtotal;

    public InvoiceDetail () {
    }

    public InvoiceDetail (int id_detail, int invoice_id, int product_id, int quantity, BigDecimal unit_price, BigDecimal subtotal) {
        this.id_detail = id_detail;
        this.invoice_id = invoice_id;
        this.product_id = product_id;
        this.quantity = quantity;
        this.unit_price = unit_price;
        this.subtotal = subtotal;
    }

    public int getId_detail() {
        return id_detail;
    }

    public void setId_detail(int id_detail) {
        this.id_detail = id_detail;
    }

    public int getInvoice_id() {
        return invoice_id;
    }

    public void setInvoice_id(int invoice_id) {
        this.invoice_id = invoice_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(BigDecimal unit_price) {
        this.unit_price = unit_price;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    @Override
    public String toString() {
        return "Detail: " + id_detail +
                ", Invoice: " + invoice_id +
                ", Product: " + product_id +
                ", Quantity: " + quantity +
                ". UnitPrice: " + unit_price +
                ", Subtotal: " + subtotal;
    }
}
