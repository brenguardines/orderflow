# Architecture

## General Approach

OrderFlow will be implemented as a modular monolith in its first version.

This approach allows the project to keep a simple deployment model while maintaining a clear separation of responsibilities between modules and layers.

The goal is to build a backend that is easy to understand, test, maintain and evolve.

---

## Main Layers

### Controller

The controller layer is responsible for receiving HTTP requests, validating input at the API boundary and delegating business operations to the service layer.

Controllers should not contain business logic.

### Service

The service layer contains the core business logic of the application.

This includes:
- order lifecycle management
- stock validation
- status transitions
- audit registration
- notification triggering

### Repository

The repository layer is responsible for database access using Spring Data JPA.

Repositories should only manage persistence operations and should not contain business rules.

### DTO

DTOs are used to transfer data between the API and the application.

They help avoid exposing internal entity structures directly to external clients.

### Entity

Entities represent the database model and are mapped using JPA annotations.

They should reflect persistence concerns, while business operations remain mostly in the service layer.

---

## Security

The system will use JWT-based authentication and role-based authorization.

Initial roles:
- ADMIN
- OPERATOR
- USER

---

## Future Evolution

The modular monolith approach allows a future migration to a distributed architecture if needed.

Possible future improvements:
- separating notification logic into an independent service
- introducing asynchronous communication
- adopting event-driven architecture
- adding observability and monitoring