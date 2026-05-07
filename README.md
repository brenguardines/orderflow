# OrderFlow

## Description
OrderFlow is a backend system designed to manage orders, products, stock and notifications in business environments.

The project focuses on real-world backend development concepts such as business rules, order lifecycle management, audit logging, role-based security and future scalability.

## Goal
The goal of this project is to build a robust backend that simulates enterprise scenarios, including:
- order management
- stock control
- audit logging
- authentication and authorization
- notification handling
- technical documentation

## Main Features
- User authentication with JWT
- Role-based access control
- Product management
- Stock management
- Order creation and lifecycle management
- Audit logging
- Notification tracking

## Tech Stack
- Java
- Spring Boot
- Spring Security
- Spring Data JPA
- PostgreSQL
- Swagger / OpenAPI
- JUnit
- Mockito

## Architecture
The system will be implemented as a modular monolith in its first version.

Main layers:
- Controller
- Service
- Repository
- DTO
- Entity
- Mapper
- Exception handling
- Security
- Configuration

More details: [Architecture](docs/architecture.md)

## Database Model
The database model will be documented in the ERD section.

More details: [ERD](docs/erd.md)

## API Documentation
More details: [API](docs/api.md)

## Technical Decisions
More details: [Technical Decisions](docs/decisions.md)

## Future Improvements
- Event-driven architecture
- Microservices migration
- Advanced observability
- Docker-based deployment
- Cloud deployment on GCP
- CI/CD pipeline