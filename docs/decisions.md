# Technical Decisions

This document explains the main technical decisions made during the development of OrderFlow.

The goal is not only to document what was implemented, but also why certain decisions were made.

---

## Modular Monolith

The first version of OrderFlow will be built as a modular monolith.

### Reason

A modular monolith allows faster development and simpler deployment while keeping the codebase organized by responsibilities.

This is a good starting point before introducing distributed system complexity.

### Trade-off

This approach is simpler than microservices, but the code must remain well organized to avoid becoming tightly coupled.

---

## Layered Architecture

The project will use a layered architecture with controllers, services, repositories, DTOs and entities.

### Reason

This separation improves readability, testability and maintainability.

Business logic should remain in the service layer instead of being placed in controllers.

---

## DTO Usage

DTOs will be used to avoid exposing JPA entities directly through the API.

### Reason

This provides better control over API contracts and prevents external clients from depending on internal persistence models.

---

## Backend Validations

Critical business rules will be validated in the backend.

### Reason

Even if a frontend performs validations, the backend must protect data consistency independently from the client consuming the API.

Examples:
- an order cannot be confirmed without stock
- an order must contain at least one item
- invalid status transitions must be rejected

---

## OrderItem Entity

`OrderItem` is used to represent products inside an order.

### Reason

An order can contain multiple products, and each product can appear in multiple orders.

`OrderItem` also stores:
- quantity
- unit price
- subtotal

This allows the system to preserve historical order information.

---

## Historical Price

The unit price will be stored in `OrderItem`.

### Reason

Product prices can change over time. Storing the price in the order item preserves the value used when the order was created or confirmed.

---

## Audit Logging

The system will include a generic `AuditLog` entity.

### Reason

Audit logs provide traceability for important actions, such as order creation, status changes and stock updates.

This improves maintainability and makes the system more realistic for enterprise scenarios.

---

## JWT Authentication

JWT will be used for authentication.

### Reason

JWT allows stateless authentication and is commonly used in REST APIs.

Role-based access control will be added to restrict operations according to user permissions.

---

## Database Naming Convention

The database uses snake_case for table and column names.

### Reason

Snake case is commonly used in relational databases and keeps naming consistent between tables, columns and SQL queries.

---

## Soft Delete for Products

Products will include an `active` field instead of being physically deleted from the database.

### Reason

This prevents losing historical information when a product was used in previous orders.

For example, if a product is no longer available, old orders should still keep a reference to it.