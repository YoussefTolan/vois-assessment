This repository contains:

- **warehousing-service** (Spring Boot backend, port 8080)   
- **frontend** (React app, port 3000)  
- **postgres** database (port 5432)  

Everything is wired up via Docker Compose.

---

## Prerequisites

- Git  
- Docker (>= 20.10)  
- Docker Compose (v2 CLI)  

---

## Getting Started

1. **Clone the repo**
   
   git clone https://github.com/YoussefTolan/iot-vois-repo.git
   cd iot-monorepo

2. **Build and run docker compose file**

   docker compose build
   docker compose up

3. **Verify using postman collection in the repo:**

   http://localhost:8080

   **or verify using frontend**

   http://localhost:3000

  

   

