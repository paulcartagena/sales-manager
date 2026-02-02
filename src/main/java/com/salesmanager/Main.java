package com.salesmanager;

import com.salesmanager.dao.CustomerDAO;
import com.salesmanager.dao.InvoiceDAO;
import com.salesmanager.dao.InvoiceDetailDAO;
import com.salesmanager.dao.ProductDAO;
import com.salesmanager.models.*;
import com.salesmanager.services.InvoiceService;
import com.salesmanager.services.ProductService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static ProductService productService = new ProductService();
    private static InvoiceService invoiceService = new InvoiceService();
    private static ProductDAO productDAO = new ProductDAO();
    private static CustomerDAO customerDAO = new CustomerDAO();
    private static InvoiceDAO invoiceDAO = new InvoiceDAO();
    private static InvoiceDetailDAO invoiceDetailDAO = new InvoiceDetailDAO();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
         boolean running = true;

         while (running) {
             showMainMenu();
             int option = scanner.nextInt();
             scanner.nextLine();

             switch (option) {
                 case 1:
                     productMenu();
                     break;
                 case 2:
                     customerMenu();
                     break;
                 case 3:
                     sales();
                     break;
                 case 4:
                     reports();
                     break;
                 case 0:
                     System.out.println("Goodbye!");
                     running = false;
                     break;
                 default:
                     System.out.println("Invalid option");
             }

             if (running) {
                 System.out.println("\nPress enter to continue...");
                 scanner.nextLine();
             }
         }

        scanner.close();
    }

    public static void showMainMenu() {
        System.out.println("\nINVENTORY MANAGEMENT SYSTEM");
        System.out.println("1. Products Management");
        System.out.println("2. Customers Management");
        System.out.println("3. Sales");
        System.out.println("4. Reports");
        System.out.println("0. Exit");
        System.out.println("Select a option: ");
    }

    public static void productMenu() {
        boolean back = false;

        while (!back) {
            System.out.println("\nPRODUCTS MENU");
            System.out.println("1. List all products");
            System.out.println("2. Search product");
            System.out.println("3. Restock product");
            System.out.println("0. <-- Back to main menu");
            System.out.println("Select a option: ");

            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    listProducts();
                    break;
                case 2:
                    searchProduct();
                    break;
                case 3:
                    restockProduct();
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid option");
            }

            if (!back) {
                System.out.println("\nPress enter to continue...");
                scanner.nextLine();
            }
        }
    }

    public static void customerMenu() {
        boolean back = false;

        while (!back) {
            System.out.println("\nCUSTOMER MENU");
            System.out.println("1. List all customers");
            System.out.println("2. Search customer");
            System.out.println("3. Register new customer");
            System.out.println("0. <-- Back to main menu");
            System.out.println("Select a option: ");

            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    listCustomers();
                    break;
                case 2:
                    searchCustomer();
                    break;
                case 3:
                    registerCustomer();
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid option");
            }

            if (!back) {
                System.out.println("\nPress enter to continue...");
                scanner.nextLine();
            }
        }
    }

    public static void sales() {
        boolean back = false;

        while (!back) {
            System.out.println("\nSALES MENU");
            System.out.println("1. Create new sale");
            System.out.println("2. List all invoices");
            System.out.println("0. <-- Back to main menu");
            System.out.println("Select a option: ");

            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    createSale();
                    break;
                case 2:
                    listInvoices();
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid option");
            }

            if (!back) {
                System.out.println("\nPress enter to continue...");
                scanner.nextLine();
            }
        }
    }

    public static void reports() {
        boolean back = false;

        while (!back) {
            System.out.println("\nREPORTS MENU");
            System.out.println("1. Products with low stock");
            System.out.println("2. Products with high stock");
            System.out.println("3. Search invoice details");
            System.out.println("0. <-- Back to main menu");
            System.out.println("Select a option: ");

            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    productsLowStock();
                    break;
                case 2:
                    productsHighStock();
                    break;
                case 3:
                    searchInvoiceDetails();
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid option");
            }

            if (!back) {
                System.out.println("\nPress enter to continue...");
                scanner.nextLine();
            }
        }
    }

    // PRODUCTS
    public static void listProducts() {
        System.out.println("\nALL PRODUCTS");
        try {
            List<Product> products = productDAO.findAll();

            if (products.isEmpty()) {
                System.out.println("No products found");
            } else {
                for (Product p : products) {
                    System.out.println(p);
                }
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void searchProduct() {
        System.out.println("\nSEARCH PRODUCT");
        try {
            System.out.println("Name");
            String name = scanner.nextLine();

            Product product = productDAO.findByName(name);

            System.out.println("Product found");
            System.out.println("Id: " + product.getId_product());
            System.out.println("Name: " + product.getName());
            System.out.println("Price: " + product.getPrice());
            System.out.println("Stock: " + product.getStock());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void restockProduct() {
        System.out.println("\nRESTOCK PRODUCT");
        try {
            System.out.println("Id product");
            int id = scanner.nextInt();

            System.out.println("Quantity");
            int quantity = scanner.nextInt();
            scanner.nextLine();

            productService.restockProduct(id, quantity);
            System.out.println("Restock successful");

        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Invalid input");
            scanner.nextLine();
        }
    }

    // CUSTOMERS
    public static void listCustomers() {
        System.out.println("\nALL CUSTOMERS");
        try {
            List<Customer> customers = customerDAO.findAll();

            if (customers.isEmpty()) {
                System.out.println("No customers found");
            } else {
                for (Customer c : customers) {
                    System.out.println(c);
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void searchCustomer() {
        System.out.println("\nSEARCH CUSTOMER");
        try {
            System.out.println("Id: ");
            int id = scanner.nextInt();

            Customer customer = customerDAO.findById(id);

            System.out.println("Product found");
            System.out.println("Id: " + customer.getId_customer());
            System.out.println("Name: " + customer.getName());
            System.out.println("Email: " + customer.getEmail());
            System.out.println("Phone: " + customer.getPhone());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void registerCustomer() {
        System.out.println("\nINSERT CUSTOMER");
        try {
            System.out.println("Name:");
            String name = scanner.nextLine();
            System.out.println("Email:");
            String email = scanner.nextLine();
            System.out.println("Phone");
            String phone = scanner.nextLine();

            Customer customer = new Customer(name, email, phone);
            Customer inserted = customerDAO.insert(customer);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // SALES
    public static void createSale() {
        System.out.println("\nCREATE SALE");
        try {
            System.out.println("CustomerId: ");
            int customerId = scanner.nextInt();
            scanner.nextLine();

            List<SaleItem> items = new ArrayList<>();
            boolean addingProducts = true;

            while (addingProducts) {
                System.out.println("ProductId: ");
                int productId = scanner.nextInt();
                System.out.println("Quantity: ");
                int quantity = scanner.nextInt();
                scanner.nextLine();

                items.add(new SaleItem(productId, quantity));

                System.out.println("Add another product? (y/n): ");
                String response = scanner.nextLine();
                addingProducts = response.equalsIgnoreCase("y");

            }

            if (items.isEmpty()) {
                System.out.println("No products added");
                return;
            }

            Invoice invoice = invoiceService.createSale(customerId, items);
            System.out.println("\nInvoice created: #" + invoice.getId_invoice());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void listInvoices() {
        System.out.println("\nALL INVOICES");
        try {
            List<Invoice> invoices = invoiceDAO.findAll();

            if (invoices.isEmpty()) {
                System.out.println("Invoices not found");
            } else {
                for (Invoice i : invoices) {
                    System.out.println(i);
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // REPORTS
    public static void productsLowStock() {
        System.out.println("\nPRODUCTS LOW STOCK");
        try {
            System.out.println("Min stock: ");
            int minStock = scanner.nextInt();

            List<Product> products  = productDAO.findLowStock(minStock);
            if (products.isEmpty()) {
                System.out.println("No products with low stock");
            }

            System.out.println("Products found");
            scanner.nextLine();

            for (Product p: products) {
                System.out.println(
                        "Name: " + p.getName() + ", Stock: " + p.getStock()
                );
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void productsHighStock() {
        System.out.println("\nPRODUCTS HIGH STOCK");
        try {
            System.out.println("High stock: ");
            int highStock = scanner.nextInt();

            List<Product> products  = productDAO.findHighStock(highStock);
            if (products.isEmpty()) {
                System.out.println("No products with high stock");
            }

            System.out.println("Products found");
            scanner.nextLine();

            for (Product p: products) {
                System.out.println(
                        "Name: " + p.getName() + ", Stock: " + p.getStock()
                );
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void searchInvoiceDetails() {
        System.out.println("\nSEARCH INVOICE DETAILS");
        try {
            System.out.println("Invoice id: ");
            int invoiceId = scanner.nextInt();
            scanner.nextLine();

            Invoice invoice = invoiceDAO.findById(invoiceId);
            if (invoice == null) {
                System.out.println("Invoice not found");
                return;
            }

            List<InvoiceDetail> details = invoiceDetailDAO.findByInvoiceId(invoiceId);

            System.out.println("\nInvoice #" + invoice.getId_invoice());
            System.out.println("Date: " + invoice.getInvoice_date());
            System.out.println("Customer ID: " + invoice.getCustomer_id());
            System.out.println("\nProducts:");

            for (InvoiceDetail d : details) {
                Product p = productDAO.findById(d.getProduct_id());
                System.out.println(" - " + p.getName() +
                        " x " + d.getQuantity() +
                        " @ " + d.getUnit_price() +
                        " = $" + d.getSubtotal()

                );
            }

            System.out.println("\nSubtotal: S" + invoice.getSubtotal());
            System.out.println("Tax: S" + invoice.getTax());
            System.out.println("Total: $" + invoice.getTotal());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}