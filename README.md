# Job Portal Management System (Microservices)

![Java](https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen?style=for-the-badge&logo=springboot)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue?style=for-the-badge&logo=postgresql)
![Docker](https://img.shields.io/badge/Docker-Enabled-blue?style=for-the-badge&logo=docker)

## 🚀 Overview
A scalable Job Portal backend built using **Spring Boot Microservices Architecture**. This system demonstrates real-world distributed system concepts including service discovery, API gateway routing, centralized configuration, circuit breaking, and event-driven inter-service communication.

---

## 🛠 Tech Stack

| Category | Technology |
| :--- | :--- |
| **Backend** | Spring Boot, Spring Cloud, Java 21 |
| **Database** | PostgreSQL |
| **Build Tool** | Maven |
| **Service Discovery** | Eureka Server |
| **API Gateway** | Spring Cloud Gateway |
| **Config Management** | Spring Cloud Config Server |
| **Security** | JWT (JSON Web Tokens) |
| **Caching** | Redis |
| **Messaging** | RabbitMQ |
| **Resilience** | Resilience4j (Circuit Breaker) |
| **Documentation** | Swagger / OpenAPI (Centralized via Gateway) |

---

## 🧩 Microservices Architecture

### 1. User Service
* User registration & login.
* JWT-based authentication.
* Role-based access (In Progress).
* Redis caching for session/user data.

### 2. Job Service
* Job creation, updates, and lifecycle management.
* Handles job status (Open/Closed/Draft).

### 3. Application Service
* Manages the "Apply" workflow.
* Tracks user-job relationships and application status.
* Integrates with other services via event-driven patterns.

### 4. Resume Service
* Resume upload and retrieval.
* Handles multipart file support and storage logic.

### 5. Search Service
* Optimized job searching with filters (keyword, location).
* Implemented with **Resilience4j** circuit breakers to ensure fault tolerance.

### 6. Analytics Service
* Aggregated data insights across all services.
* Provides system-level metrics (Total users, job activity, trends).

### 7. Notification Service
* Asynchronous communication using **RabbitMQ**.
* Triggers emails/alerts based on system events (e.g., successful application).

---

## 🌐 Core Infrastructure

### API Gateway
* **Central Entry Point:** All client requests hit the Gateway at `localhost:8080`.
* **Dynamic Routing:** Uses Eureka to route requests to available service instances.
* **Swagger Aggregation:** Hosts a centralized Swagger UI for all microservices.

### Eureka Server
* Acts as the Service Registry where all microservice instances register themselves.

### Config Server
* Externalizes configuration. Fetches properties from a centralized GitHub repository or local file system.

---

## 📡 API Access & Documentation

### Swagger UI
Access the interactive API documentation at:
`http://localhost:8080/swagger-ui/index.html`

### Gateway Routing
All APIs are accessible through the unified gateway URL:
`http://localhost:8080/api/{service-name}/...`

| Service | Example Endpoint |
| :--- | :--- |
| **User** | `POST /api/users/register` |
| **Job** | `GET /api/jobs` |
| **Search** | `GET /api/search/jobs?keyword=java` |
| **Analytics** | `GET /api/analytics/summary` |

---

## ⚙️ Running the Project

### Prerequisites
* **Java 21** or higher
* **Maven**
* **PostgreSQL** (Running instance)
* **Docker** (Required for RabbitMQ / Zipkin)

### Start-Up Order (Crucial)
To ensure the system initializes correctly, start the services in this specific order:

1.  **Config Server**: Wait for it to be fully up.
2.  **Eureka Server**: The registry for all other services.
3.  **Infrastructure Services**: Start RabbitMQ and Redis (via Docker).
4.  **Microservices**: User, Job, Application, Resume, Search, Analytics, Notification.
5.  **API Gateway**: The final entry point.

---

## 📦 Features Implemented
- [x] Full Microservices Orchestration
- [x] Dynamic Service Discovery (Eureka)
- [x] Centralized Configuration (Spring Cloud Config)
- [x] Secure Authentication with JWT
- [x] Fault Tolerance with Circuit Breakers (Resilience4j)
- [x] Event-driven communication with RabbitMQ
- [x] Centralized Swagger API Documentation

'## 🚧 Work in Progress
- [ ] **RBAC:** Implementation of Role-Based Access Control.
- [ ] **Distributed Tracing:** Adding Zipkin/Sleuth for request tracking.
- [ ] **Dockerization:** Creating a `docker-compose.yml` for the entire stack.'

---

## 📌 Author
**Pranjal Jamuar**
