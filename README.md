# GeoProximity – Microservices Backend (Spring Boot)

## 📌 Overview

**GeoProximity** is a Spring Boot–based **microservices backend** focused on **geo-aware processing, asynchronous pipelines, and scalable service-to-service communication**.
The current repository reflects an **early but solid microservices foundation**, built around **service discovery, Kafka-based messaging, and background workers**.

This project is intentionally designed to evolve toward a **location-based, event-driven platform**.

---

## 🗂️ Repository Structure

```
GeoProximity/
│
├── business-service/        # Core business logic + Kafka producer
├── es-worker-service/       # Background worker (Kafka consumer / ES indexing)
├── register-server/         # Eureka Service Registry
├── README.md
```

---

## 🧠 Architectural Principles

* Microservice architecture
* Event-driven communication (Kafka)
* Loose coupling between services
* Horizontal scalability
* Clean separation of concerns

---

## 🏗️ High-Level Architecture

```
            ┌──────────────────┐
            │  Business Service │
            │ (Kafka Producer)  │
            └────────┬─────────┘
                     │
               Kafka Topics
                     │
            ┌────────▼─────────┐
            │ ES Worker Service │
            │ (Kafka Consumer)  │
            └────────┬─────────┘
                     │
             Elasticsearch (future)

        ┌─────────────────────────┐
        │     Eureka Registry      │
        │   (register-server)     │
        └─────────────────────────┘
```

---

## 🔧 Tech Stack

### Core Technologies

* **Java 17**
* **Spring Boot 3.x**
* **Spring Cloud Netflix Eureka**
* **Apache Kafka**

### Data & Messaging

* **Kafka Producer / Consumer**
* **Elasticsearch (planned / external)**

### Dev & Infra

* **Docker (planned)**
* **Git / GitHub**

---

## 🧩 Microservices Description

### 1️⃣ register-server (Eureka Server)

**Role:** Service Discovery

* Central registry for all microservices
* Enables dynamic service registration
* Avoids hard-coded service URLs

**Port:** `8761`

---

### 2️⃣ business-service

**Role:** Core business logic + event producer

Responsibilities:

* Handles main domain operations
* Produces Kafka events when business actions occur
* Acts as the entry point for future APIs

Key Features:

* Kafka producer configuration
* Clean separation between controller, service, and messaging layers

---

### 3️⃣ es-worker-service

**Role:** Asynchronous background worker

Responsibilities:

* Consumes Kafka events
* Processes data asynchronously
* Prepares data for indexing/search use cases

Typical Use Cases:

* Elasticsearch indexing
* Geo-based search preparation
* Analytics pipelines

---

## 📡 Event-Driven Flow (Kafka)

Example flow:

1. Business Service performs an operation
2. Event is published to Kafka
3. ES Worker Service consumes the event
4. Data is processed / indexed asynchronously

This design ensures:

* High throughput
* Non-blocking operations
* Easy extensibility with new consumers

---

## 🔐 Service Communication

* Services discover each other via **Eureka**
* Kafka decouples producers from consumers
* No direct tight coupling between services

---

## 🚀 Running the Project (Basic)

### Prerequisites

* Java 17+
* Maven
* Kafka (local or Docker)

### Run Eureka Server

```bash
cd register-server
mvn spring-boot:run
```

### Run Business Service

```bash
cd business-service
mvn spring-boot:run
```

### Run ES Worker Service

```bash
cd es-worker-service
mvn spring-boot:run
```

---

## 🛣️ Roadmap

* Add API Gateway
* Add Keycloak authentication
* Add Redis caching
* Integrate Elasticsearch fully
* Add geo-proximity queries
* Docker Compose setup
* Kubernetes deployment

---

## 👨‍💻 Author

**Boutmedjet**
Software Engineering Student
Backend & Distributed Systems

---

## ⭐ Notes

This repository represents a **clean starting point** for a scalable, geo-aware, event-driven backend. The architecture is intentionally simple but follows **real-world system design principles** to allow future growth.
