# Spring Boot + GemFire + MySQL (Dockerized)

This is a Proof of Concept (POC) demonstrating a high-performance caching architecture. 
It uses Spring Boot for the backend, MySQL for persistence, and Apache Geode (GemFire) for
Look-Aside Caching.

## 🚀 Architecture
Service: Spring Boot (Java 17)
Database: MySQL 8.0
Cache: Apache Geode (GemFire)
Containerization: Docker & Docker Compose

## 🛠️ How to Run
Prerequisite: Install Docker Desktop.

1.  Build the Project:
    ./mvnw clean package -DskipTests

2.  Start the Infrastructure:
    docker compose up -d --build

3.  Access the Application:
    Swagger UI: (http://localhost:9090/swagger-ui/index.html)
    Port Mapping: The app runs on port `8080` internally but is exposed on `9090` to avoid conflicts.

##  How to Test
1.  Create Product: POST (Saved to DB).
2.  Get Product (1st time): (Get) Returns from DB (Slow ~3s).
3.  Get Product (2nd time): (Get) Returns from GemFire Cache (Fast ~10ms).
4.  Update Product :  (Put) Updates data in DB and cache either
5.  Delete product : (Delete) Deletes data from DB and Cache either