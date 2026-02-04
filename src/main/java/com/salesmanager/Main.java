package com.salesmanager;

import com.salesmanager.models.*;
import com.salesmanager.services.CustomerService;
import com.salesmanager.services.InvoiceService;
import com.salesmanager.services.ProductService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static ProductService productService = new ProductService();
    private static InvoiceService invoiceService = new InvoiceService();;
    private static CustomerService customerService = new CustomerService();
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
            System.out.println("1. Create product");
            System.out.println("2. List all products");
            System.out.println("3. Search product");
            System.out.println("4. Restock product");
            System.out.println("0. <-- Back to main menu");
            System.out.println("Select a option: ");

            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    createProduct();
                    break;
                case 2:
                    getAllProducts();
                    break;
                case 3:
                    getProductById();
                    break;
                case 4:
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
            System.out.println("1. Register new customer");
            System.out.println("2. List all customers");
            System.out.println("3. Search customer");
            System.out.println("4. Update customer");
            System.out.println("5. Delete customer");
            System.out.println("0. <-- Back to main menu");
            System.out.println("Select a option: ");

            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    createCustomer();
                    break;
                case 2:
                    getAllCustomers();
                    break;
                case 3:
                    getCustomerById();
                    break;
                case 4:
                    updateCustomer();
                    break;
                case 5:
                    deleteCustomer();
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
                    getAllInvoices();
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
            System.out.println("1. Search invoice details");
            System.out.println("0. <-- Back to main menu");
            System.out.println("Select a option: ");

            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    getDtByInvoiceId();
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
    public static void createProduct() {
        System.out.println("\nCREATE PRODUCT");
        try {
            System.out.println("Name:");
            String name = scanner.nextLine();
            System.out.println("Price:");
            BigDecimal price = scanner.nextBigDecimal();
            System.out.println("Stock:");
            int stock = scanner.nextInt();

            Product product = new Product(name, price, stock);
            Product inserted = productService.createProduct(product);

            System.out.println("Product created successfully");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void getAllProducts() {
        System.out.println("\nALL PRODUCTS");
        try {
            List<Product> products = productService.getAllProducts();

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

    public static void getProductById() {
        System.out.println("\nSEARCH PRODUCT BY ID");
        try {
            System.out.println("ID:");
            int id = scanner.nextInt();

            Product product = productService.getProductById(id);

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
    public static void createCustomer() {
        System.out.println("\nCREATE CUSTOMER");
        try {
            System.out.println("Name:");
            String name = scanner.nextLine();
            System.out.println("Email:");
            String email = scanner.nextLine();
            System.out.println("Phone");
            String phone = scanner.nextLine();

            Customer customer = new Customer(name, email, phone);
            Customer inserted = customerService.createCustomer(customer);

            System.out.println("Customer created successfully");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void getAllCustomers() {
        System.out.println("\nALL CUSTOMERS");
        try {
            List<Customer> customers = customerService.getAllCustomers();

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

    public static void getCustomerById() {
        System.out.println("\nSEARCH CUSTOMER BY ID");
        try {
            System.out.println("ID:");
            int id = scanner.nextInt();

            Customer customer = customerService.getCustomerById(id);

            System.out.println("Customer found");
            System.out.println("Id: " + customer.getId_customer());
            System.out.println("Name: " + customer.getName());
            System.out.println("Email: " + customer.getEmail());
            System.out.println("Phone: " + customer.getPhone());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void updateCustomer() {
        System.out.println("\nUPDATE CUSTOMER");
        try {
            System.out.println("ID:");
            int id = scanner.nextInt();
            scanner.nextLine();
            System.out.println("New name:");
            String name = scanner.nextLine();
            System.out.println("New email:");
            String email = scanner.nextLine();
            System.out.println("New phone:");
            String phone = scanner.nextLine();

            Customer customer = new Customer(id, name, email, phone);
            Customer updated = customerService.updateCustomer(customer);

            System.out.println("Customer updated successfully");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void deleteCustomer() {
        System.out.println("\nDELETE CUSTOMER");
        try {
            System.out.println("ID:");
            int id = scanner.nextInt();

            customerService.deleteCustomer(id);

            System.out.println("Customer deleted successful");
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

    public static void getAllInvoices() {
        System.out.println("\nALL INVOICES");
        try {
            List<Invoice> invoices = invoiceService.getAllInvoices();

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
    public static void getDtByInvoiceId() {
        System.out.println("\nSEARCH INVOICE DETAILS");
        try {
            System.out.println("Invoice id: ");
            int invoiceId = scanner.nextInt();
            scanner.nextLine();

            Invoice invoice = invoiceService.getInvoiceById(invoiceId);
            if (invoice == null) {
                System.out.println("Invoice not found");
                return;
            }

            List<InvoiceDetail> details = invoiceService.getDtByInvoiceId(invoiceId);

            System.out.println("\nInvoice #" + invoice.getId_invoice());
            System.out.println("Date: " + invoice.getInvoice_date());
            System.out.println("Customer ID: " + invoice.getCustomer_id());
            System.out.println("\nProducts:");

            for (InvoiceDetail d : details) {
                Product p = productService.getProductById(d.getProduct_id());
                System.out.println(" - " + p.getName() +
                        " x " + d.getQuantity() +
                        " @ " + d.getUnit_price() +
                        " = $" + d.getSubtotal()

                );
            }

            System.out.println("\nSubtotal: $" + invoice.getSubtotal());
            System.out.println("Tax: $" + invoice.getTax());
            System.out.println("Total: $" + invoice.getTotal());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}