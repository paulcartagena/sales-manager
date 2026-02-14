# SaleManager
SaleManager is a Java console application that manages product sales, invoices,
customers, and inventory using a layered architecture and PostgreSQL.

## Features
- Product management (CRUD)
- Stock control
- Invoice and invoice detail generation
- Customer validation
- Tax calculation
- Custom exception handling
- Environment variables for secure database configuration

## Tech Stack
- Java
- Maven
- PostgreSQL
- JDBC
- IntelliJ IDEA

## Project Structure
- `config/` - Database connection
- `dao/` - Data access layer (JDBC)
- `models/` - Entities
- `services/` - Business logic
- `exceptions/` - Custom exceptions

## Setup
1. Create a PostgreSQL database
2. Run `schema.sql` to create the tables
3. Configure the following environment variables:
    - `DB_URL` → jdbc:postgresql://localhost:5432/your_database
    - `DB_USER` → your PostgreSQL username
    - `DB_PASSWORD` → your PostgreSQL password
4. Run the application
