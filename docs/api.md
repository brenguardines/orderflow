# API Documentation

This document describes the initial API design for OrderFlow.

The API may evolve during development as business rules become more detailed.

---

## Auth

### Register User

Endpoint: `POST /auth/register`

Creates a new user.

### Login

Endpoint: `POST /auth/login`

Authenticates a user and returns a JWT token.

---

## Users

### Get Users

Endpoint: `GET /users`

Returns a list of users.

### Get User by ID

Endpoint: `GET /users/{id}`

Returns a specific user.

### Update User Role

Endpoint: `PATCH /users/{id}/role`

Updates the role of a user.

---

## Products

### Create Product

Endpoint: `POST /products`

Creates a new product.

### Get Products

Endpoint: `GET /products`

Returns a list of products.

Possible future filters:
- active
- sku
- name

### Get Product by ID

Endpoint: `GET /products/{id}`

Returns a specific product.

### Update Product

Endpoint: `PUT /products/{id}`

Updates product information.

### Update Product Stock

Endpoint: `PATCH /products/{id}/stock`

Updates the stock of a product.

### Delete Product

Endpoint: `DELETE /products/{id}`

Deletes or deactivates a product.

---

## Orders

### Create Order

Endpoint: `POST /orders`

Creates a new order with one or more items.

### Get Orders

Endpoint: `GET /orders`

Returns a list of orders.

Possible future filters:
- status
- createdBy
- date range

### Get Order by ID

Endpoint: `GET /orders/{id}`

Returns a specific order with its items.

### Confirm Order

Endpoint: `POST /orders/{id}/confirm`

Confirms an order if stock is available.

### Cancel Order

Endpoint: `POST /orders/{id}/cancel`

Cancels an order and restores stock if needed.

### Update Order Status

Endpoint: `PATCH /orders/{id}/status`

Updates the status of an order following valid transitions.

---

## Audit Logs

### Get Audit Logs

Endpoint: `GET /audit-logs`

Returns audit records.

### Get Audit Logs by Entity

Endpoint: `GET /audit-logs/{entityType}/{entityId}`

Returns audit records for a specific entity.

---

## Notifications

### Get Notifications

Endpoint: `GET /notifications`

Returns generated notifications.

### Test Notification

Endpoint: `POST /notifications/test`

Generates a test notification.