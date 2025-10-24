
# 🚗 Smart Parking System

A **modular, production-ready Smart Parking System** built with **Spring Boot**, **Gradle**, and **PostgreSQL**, featuring ticket management, fee calculation, and concurrency-safe parking spot allocation.

---

## 🧩 Architecture

The system is split into three logical modules for clean separation of concerns:

| Module | Purpose |
|---------|----------|
| **core** | Business logic (fee calculation, spot allocation, ticket generation) |
| **persistence** | Database entities and repositories (JPA with PostgreSQL) |
| **api** | REST controllers and endpoints for parking operations |

Each module has its own `build.gradle`, and the root `settings.gradle` includes all submodules.

---

## ⚙️ Features

- Vehicle check-in and check-out APIs  
- Automatic ticket number generation  
- Parking fee calculator (based on duration and vehicle type)  
- Floor and spot allocation per vehicle type  
- Spring Data JPA + PostgreSQL persistence  
- Optional Redis for caching / real-time availability  
- Docker-based local development setup  

---

## 🧠 Prerequisites

- **Java 17+**
- **Gradle 8+**
- **Docker** (for PostgreSQL and Redis)

---

## 🐘 Quick Start (Recommended)

### 1️⃣ Start PostgreSQL (and Redis if configured)

```bash
docker-compose up -d
```

*(Make sure you have a `docker-compose.yml` file with PostgreSQL service defined)*

---

### 2️⃣ Run the Spring Boot App

```bash
./gradlew :api:bootRun
```

This starts the REST API at:  
👉 `http://localhost:8080`

---

## 🧪 API Testing

### 🚙 Check-in Vehicle

**POST**
```
http://localhost:8080/parking/entry?licenseNumber=KA01AB1234&type=CAR
```

✅ Response example:
```json
{
  "ticketNumber": "TKT-20251018-00001",
  "spotNumber": "F1-S05",
  "entryTime": "2025-10-18T12:15:00"
}
```

---

### 🚗 Check-out Vehicle

**POST**
```
http://localhost:8080/parking/exit?ticketNumber=TKT-20251018-00001
```

✅ Response example:
```json
{
  "ticketNumber": "TKT-20251018-00001",
  "durationMinutes": 95,
  "totalFee": 120,
  "message": "Thank you for using Smart Parking!"
}
```

---

## 🧱 Optional: In-Memory Database (H2)

If you don’t want to install PostgreSQL, switch to H2 for local testing by updating `application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:h2:mem:testdb
    driver-class-name: org.h2.Driver
    username: sa
    password:
```

---

## 🧰 Development Structure

```
smart-parking/
├── api/
│   ├── src/main/java/com/example/parking/api
│   ├── build.gradle
│   └── ...
├── core/
│   ├── src/main/java/com/example/parking/core
│   ├── build.gradle
│   └── ...
├── persistence/
│   ├── src/main/java/com/example/parking/persistence
│   ├── build.gradle
│   └── ...
├── docker-compose.yml
├── settings.gradle
└── README.md
```

---

## 🧩 Next Steps

- ✅ Add test data seeders for default floors and parking spots  
- ✅ Implement test cases using JUnit and MockMvc  
- 🔄 Add Redis cache for availability tracking  
- 🧾 Add admin dashboard for spot monitoring (future enhancement)

---

