# API Documentation

This document describes the initial API design for OrderFlow.

The API may evolve during development as business rules become more detailed.

---

## Overview

OrderFlow exposes a REST API focused on order management, stock control, audit logging and notifications.

The API follows these general principles:
- JSON is used for request and response bodies.
- Authentication will be handled using JWT.
- Critical business validations are performed in the backend.
- Error responses follow a consistent structure.

---

## Authentication

Protected endpoints require a JWT token in the `Authorization` header.

Example:

```http
Authorization: Bearer <token>
```

---

## Auth

### Register User

Endpoint: `POST /auth/register`

Creates a new user.

Request body:

```json
{
  "name": "Brenda Guardines",
  "email": "brenda@example.com",
  "password": "securePassword123",
  "role": "OPERATOR"
}
```

Response body:

```json
{
  "id": 1,
  "name": "Brenda Guardines",
  "email": "brenda@example.com",
  "role": "OPERATOR",
  "active": true
}
```

Validations:
- name is required
- email is required
- email must be unique
- password is required
- role is required

Possible errors:
- 400 Bad Request
- 409 Conflict if email already exists

---

### Login

Endpoint: `POST /auth/login`

Authenticates a user and returns a JWT token.

Request body:

```json
{
  "email": "brenda@example.com",
  "password": "securePassword123"
}
```

Response body:

```json
{
  "token": "jwt-token",
  "type": "Bearer"
}
```

Possible errors:
- 400 Bad Request
- 401 Unauthorized

---

## Users

### Get Users

Endpoint: `GET /users`

Returns a list of users.

Required role:
- ADMIN

Response body:

```json
[
  {
    "id": 1,
    "name": "Brenda Guardines",
    "email": "brenda@example.com",
    "role": "OPERATOR",
    "active": true
  }
]
```

Possible errors:
- 401 Unauthorized
- 403 Forbidden

---

### Get User by ID

Endpoint: `GET /users/{id}`

Returns a specific user.

Required role:
- ADMIN

Response body:

```json
{
  "id": 1,
  "name": "Brenda Guardines",
  "email": "brenda@example.com",
  "role": "OPERATOR",
  "active": true
}
```

Possible errors:
- 401 Unauthorized
- 403 Forbidden
- 404 Not Found

---

### Update User Role

Endpoint: `PATCH /users/{id}/role`

Updates the role of a user.

Required role:
- ADMIN

Request body:

```json
{
  "role": "ADMIN"
}
```

Response body:

```json
{
  "id": 1,
  "name": "Brenda Guardines",
  "email": "brenda@example.com",
  "role": "ADMIN",
  "active": true
}
```

Validations:
- role is required
- role must be valid

Possible errors:
- 400 Bad Request
- 401 Unauthorized
- 403 Forbidden
- 404 Not Found

---

## Products

### Create Product

Endpoint: `POST /products`

Creates a new product.

Required role:
- ADMIN
- OPERATOR

Request body:

```json
{
  "name": "Notebook Lenovo",
  "description": "Business laptop",
  "sku": "NOTEBOOK-001",
  "stock": 10,
  "price": 1200.00
}
```

Response body:

```json
{
  "id": 1,
  "name": "Notebook Lenovo",
  "description": "Business laptop",
  "sku": "NOTEBOOK-001",
  "stock": 10,
  "price": 1200.00,
  "active": true
}
```

Validations:
- name is required
- sku is required
- sku must be unique
- stock must be greater than or equal to 0
- price must be greater than 0

Possible errors:
- 400 Bad Request
- 401 Unauthorized
- 403 Forbidden
- 409 Conflict if sku already exists

---

### Get Products

Endpoint: `GET /products`

Returns a list of products.

Possible future filters:
- active
- sku
- name

Response body:

```json
[
  {
    "id": 1,
    "name": "Notebook Lenovo",
    "description": "Business laptop",
    "sku": "NOTEBOOK-001",
    "stock": 10,
    "price": 1200.00,
    "active": true
  }
]
```

Possible errors:
- 401 Unauthorized

---

### Get Product by ID

Endpoint: `GET /products/{id}`

Returns a specific product.

Response body:

```json
{
  "id": 1,
  "name": "Notebook Lenovo",
  "description": "Business laptop",
  "sku": "NOTEBOOK-001",
  "stock": 10,
  "price": 1200.00,
  "active": true
}
```

Possible errors:
- 401 Unauthorized
- 404 Not Found

---

### Update Product

Endpoint: `PUT /products/{id}`

Updates product information.

Required role:
- ADMIN
- OPERATOR

Request body:

```json
{
  "name": "Notebook Lenovo ThinkPad",
  "description": "Updated business laptop",
  "sku": "NOTEBOOK-001",
  "stock": 12,
  "price": 1300.00
}
```

Response body:

```json
{
  "id": 1,
  "name": "Notebook Lenovo ThinkPad",
  "description": "Updated business laptop",
  "sku": "NOTEBOOK-001",
  "stock": 12,
  "price": 1300.00,
  "active": true
}
```

Possible errors:
- 400 Bad Request
- 401 Unauthorized
- 403 Forbidden
- 404 Not Found
- 409 Conflict if sku already exists

---

### Update Product Stock

Endpoint: `PATCH /products/{id}/stock`

Updates the stock of a product.

Required role:
- ADMIN
- OPERATOR

Request body:

```json
{
  "stock": 15
}
```

Response body:

```json
{
  "id": 1,
  "name": "Notebook Lenovo",
  "sku": "NOTEBOOK-001",
  "stock": 15
}
```

Validations:
- stock must be greater than or equal to 0

Possible errors:
- 400 Bad Request
- 401 Unauthorized
- 403 Forbidden
- 404 Not Found

---

### Delete Product

Endpoint: `DELETE /products/{id}`

Deactivates a product using soft delete.

Required role:
- ADMIN

Response body:

```json
{
  "id": 1,
  "active": false,
  "message": "Product deactivated successfully"
}
```

Business rules:
- product is not physically removed from the database
- product remains available for historical order references

Possible errors:
- 401 Unauthorized
- 403 Forbidden
- 404 Not Found

---

## Orders

### Create Order

Endpoint: `POST /orders`

Creates a new order with one or more items.

Required role:
- ADMIN
- OPERATOR

Request body:

```json
{
  "customerName": "Cliente Empresa S.A.",
  "items": [
    {
      "productId": 1,
      "quantity": 2
    },
    {
      "productId": 3,
      "quantity": 1
    }
  ]
}
```

Response body:

```json
{
  "id": 1,
  "customerName": "Cliente Empresa S.A.",
  "status": "CREATED",
  "totalAmount": 2500.00,
  "items": [
    {
      "productId": 1,
      "productName": "Notebook Lenovo",
      "quantity": 2,
      "unitPrice": 1200.00,
      "subtotal": 2400.00
    },
    {
      "productId": 3,
      "productName": "Mouse Logitech",
      "quantity": 1,
      "unitPrice": 100.00,
      "subtotal": 100.00
    }
  ]
}
```

Validations:
- customerName is required
- order must contain at least one item
- productId is required
- quantity must be greater than 0
- product must exist
- product must be active

Possible errors:
- 400 Bad Request
- 401 Unauthorized
- 403 Forbidden
- 404 Not Found if product does not exist

---

### Get Orders

Endpoint: `GET /orders`

Returns a list of orders.

Possible future filters:
- status
- createdBy
- date range

Response body:

```json
[
  {
    "id": 1,
    "customerName": "Cliente Empresa S.A.",
    "status": "CREATED",
    "totalAmount": 2500.00
  }
]
```

Possible errors:
- 401 Unauthorized

---

### Get Order by ID

Endpoint: `GET /orders/{id}`

Returns an order with its items.

Response body:

```json
{
  "id": 1,
  "customerName": "Cliente Empresa S.A.",
  "status": "CONFIRMED",
  "totalAmount": 2500.00,
  "createdBy": {
    "id": 1,
    "name": "Brenda Guardines"
  },
  "items": [
    {
      "productId": 1,
      "productName": "Notebook Lenovo",
      "quantity": 2,
      "unitPrice": 1200.00,
      "subtotal": 2400.00
    }
  ]
}
```

Possible errors:
- 401 Unauthorized
- 404 Not Found

---

### Confirm Order

Endpoint: `POST /orders/{id}/confirm`

Confirms an order if stock is available.

Required role:
- ADMIN
- OPERATOR

Response body:

```json
{
  "id": 1,
  "status": "CONFIRMED",
  "message": "Order confirmed successfully"
}
```

Business rules:
- order must exist
- order status must be CREATED
- stock must be available for all items
- stock is decreased after confirmation
- audit log is generated
- notification may be generated

Possible errors:
- 400 Bad Request if transition is invalid
- 401 Unauthorized
- 403 Forbidden
- 404 Not Found
- 409 Conflict if stock is not enough

---

### Cancel Order

Endpoint: `POST /orders/{id}/cancel`

Cancels an order.

Required role:
- ADMIN
- OPERATOR

Response body:

```json
{
  "id": 1,
  "status": "CANCELLED",
  "message": "Order cancelled successfully"
}
```

Business rules:
- order must exist
- delivered orders cannot be cancelled
- if the order was already confirmed, stock is restored
- audit log is generated
- notification may be generated

Possible errors:
- 400 Bad Request if cancellation is not allowed
- 401 Unauthorized
- 403 Forbidden
- 404 Not Found

---

### Update Order Status

Endpoint: `PATCH /orders/{id}/status`

Updates the status of an order following valid transitions.

Required role:
- ADMIN
- OPERATOR

Request body:

```json
{
  "status": "IN_PREPARATION"
}
```

Response body:

```json
{
  "id": 1,
  "status": "IN_PREPARATION",
  "message": "Order status updated successfully"
}
```

Business rules:
- order must exist
- status transition must be valid
- audit log is generated

Possible errors:
- 400 Bad Request if transition is invalid
- 401 Unauthorized
- 403 Forbidden
- 404 Not Found

---

## Audit Logs

### Get Audit Logs

Endpoint: `GET /audit-logs`

Returns audit records.

Required role:
- ADMIN

Possible errors:
- 401 Unauthorized
- 403 Forbidden

---

### Get Audit Logs by Entity

Endpoint: `GET /audit-logs/{entityType}/{entityId}`

Returns audit records for a specific entity.

Required role:
- ADMIN

Example:

```http
GET /audit-logs/ORDER/1
```

Possible errors:
- 401 Unauthorized
- 403 Forbidden

---

## Notifications

### Get Notifications

Endpoint: `GET /notifications`

Returns generated notifications.

Required role:
- ADMIN

Possible errors:
- 401 Unauthorized
- 403 Forbidden

---

### Test Notification

Endpoint: `POST /notifications/test`

Generates a test notification.

Required role:
- ADMIN

Request body:

```json
{
  "recipient": "brenda@example.com",
  "channel": "EMAIL",
  "message": "This is a test notification"
}
```

Response body:

```json
{
  "id": 1,
  "recipient": "brenda@example.com",
  "channel": "EMAIL",
  "status": "SENT"
}
```

Possible errors:
- 400 Bad Request
- 401 Unauthorized
- 403 Forbidden

---

## Error Response Format

All errors should follow a consistent response structure.

Example:

```json
{
  "timestamp": "2026-05-09T10:30:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Product stock must be greater than or equal to 0",
  "path": "/products/1/stock"
}
```