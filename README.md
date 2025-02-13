#
# **To-Do List API**

## Description

This is a Backend project developed with **Java**, **Spring Boot**, **Spring Security (JWT)** and **PostgreSQL** that provides a RESTful API for task management. It allows users to register, log in and manage their tasks through CRUD (create, read, update and delete) operations.
> link to spanish version: [README_es.md](https://github.com/jceladab/To-Do-List-API-Project-Bcknd/blob/main/README_es.md)
## Technologies used
- **Java 21**
- **Spring Boot**
- **Spring Security with JWT**
- **PostgreSQL**
- **Maven** **Docker**
- **Docker** (Optional for ease of implementation)

## Main Endpoints

The **API** provides the following endpoints:

### Authentication

POST `/users/register` → Register a new user.

POST `/users/login` → Log in and obtain a JWT token.

### Tasks (Requires authentication)

**GET** `/todos` → Get all tasks (with paging).

**POST** `/todos` → Create a new task.

**PUT** `/todos/{id}` → Update a task (only if belongs to the authenticated user).

**DELETE** `/todos/{id}` → Delete a task (only if it belongs to the authenticated user).

To make authenticated requests, include the JWT token in the header:

``` bash
Authorization: Bearer <TOKEN>
```
---
# Running requirements
Before running the project, make sure you have installed:

- **Java 21**
- **Maven**
- **PostgreSQL**
- **Git** (to clone the repository)
- **Docker and Docker Compose** (Optional, recommended for faster installation)

## Installation and execution

### 1. Clone the repository
Run the following command in a terminal:
```bash
git clone https://github.com/tu-usuario/todo-list-api.git
cd todo-list-api
```

### 2. Configure database
Make sure PostgreSQL is running and with the `all_project` database; to create it you can use the following query:
```sql
CREATE DATABASE todo_project;
```
Or if you use Docker, run:
```bash
docker compose up -d
```

### 3. Configure the ``application.properties`` file
Modify `src/main/resources/application.properties` with your PostgreSQL credentials:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/todo_project
spring.datasource.username=your_user
spring.datasource.password=your_password
```

### 4. Build and run the application
From the terminal in the project folder, run:
```bash
mvn clean install
mvn spring-boot:run
```

If you use Docker:
```bash
docker compose up -d
```

### 5. Testing the API
You can test the endpoints with **Postman**.

### **5.1. User Registration**
- **Method:** `POST` 
- **URL:** `http://localhost:8080/users/register`
- **Body (JSON):**
```json
{
  “name": "Juan Perez",
  “email": "juan@gmail.com",
  “password": ”123456”
}
```

### **5.2. Login** 
- **Method:** `POST` 
- **URL:** `http://localhost:8080/users/login`
- **Body (JSON):**
```json
{
  “email": "juan@gmail.com",
  “password": ”123456”
}
```

- **Expected response:** A JWT token.
> **Note**: copy the token received, as you will need it to test the other Endpoints. To use it you must go to the **Headers** tab to add in the **Key** field the word Authorization and in the **Value** field the word Bearer followed by the TOKEN, as follows:
> ```bash
> Authorization: Bearer <TOKEN>
> 

### **5.3. Create a task**
- **Method:** `POST`
- **URL:** `http://localhost:8080/todos`
- **Headers:**
  - `Authorization: Bearer <TOKEN>`
- **Body (JSON):**
```json
{
  “title": "Buy groceries",
  “description": ”Milk, eggs, rice, panela, and fruits.”
}
```

### **5.4. Get all tasks**
- **Method:** `GET`
- **URL:** `http://localhost:8080/todos`
- **Headers:**
  - `Authorization: Bearer <TOKEN>`

### **5.5. Update a task**
- **Method:** `PUT`
- **URL:** `http://localhost:8080/todos/{id}`
- **Headers:**
  - `Authorization: Bearer <TOKEN>`
- **Body (JSON):**
```json
{
  “title": ``complete pantry``,
  “description": ”Cereals, vegetables, and meat.”
}
```

### **5.6. Delete a task**
- **Method:** `DELETE`
- **URL:** `http://localhost:8080/todos/{id}` 
- **Headers:**
  - `Authorization: Bearer <TOKEN>`

## 6. Deploying with Docker 🐳

If you want to run the API without installing dependencies, you can use Docker:

```bash
docker build -t todo-list-api .
docker run -p 8080:8080 todo-list-api
```

Or with Docker Compose:
- Create a `docker-compose.yml` file with PostgreSQL and the API.
- Run the command:

``` bash
docker compose up -d
```

---
🚀 **Project developed by Juan Esteban Celada Bolivar**
