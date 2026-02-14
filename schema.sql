-- Database Schema

CREATE TABLE customers
(
    id_customer SERIAL PRIMARY KEY,
    name        VARCHAR(100) NOT NULL,
    email       VARCHAR(100) NOT NULL UNIQUE,
    phone       VARCHAR(20)
);

CREATE TABLE products
(
    id_product SERIAL PRIMARY KEY,
    name       VARCHAR(100)   NOT NULL,
    price      NUMERIC(10, 2) NOT NULL,
    stock      INTEGER        NOT NULL DEFAULT 0
);

CREATE TABLE invoices
(
    id_invoice   SERIAL PRIMARY KEY,
    customer_id  INTEGER        NOT NULL,
    invoice_date TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    subtotal     NUMERIC(10, 2) NOT NULL,
    tax          NUMERIC(10, 2) NOT NULL,
    total        NUMERIC(10, 2) NOT NULL,

    CONSTRAINT invoices_customer_id_fkey FOREIGN KEY (customer_id)
        REFERENCES customers (id_customer)
);

CREATE TABLE invoice_details
(
    id_detail  SERIAL PRIMARY KEY,
    invoice_id INTEGER        NOT NULL,
    product_id INTEGER        NOT NULL,
    quantity   INTEGER        NOT NULL,
    unit_price NUMERIC(10, 2) NOT NULL,
    subtotal   NUMERIC(10, 2) NOT NULL,

    CONSTRAINT invoice_details_invoice_id_fkey FOREIGN KEY (invoice_id)
        REFERENCES invoices (id_invoice),
    CONSTRAINT invoice_details_product_id_fkey FOREIGN KEY (product_id)
        REFERENCES products (id_product)
);