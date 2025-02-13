#
# **To-Do List API**

## Descripción

Este es un proyecto Backend desarrollado con **Java**, **Spring Boot**, **Spring Security (JWT)** y **PostgreSQL** que proporciona una API RESTful para la gestión de tareas. Permite a los usuarios registrarse, iniciar sesión y gestionar sus tareas mediante operaciones CRUD (crear, leer, actualizar y eliminar).

## Tecnologías utilizadas
- **Java 21**
- **Spring Boot**
- **Spring Security con JWT**
- **PostgreSQL**
- **Maven**
- **Docker** (Opcional para facilitar la ejecución)

## Endpoints Principales

La **API** proporciona los siguientes endpoints:

### Autenticación

POST `/users/register` → Registrar un nuevo usuario.

POST `/users/login` → Iniciar sesión y obtener un token JWT.

### Tareas (Requiere autenticación)

**GET** `/todos` → Obtener todas las tareas (con paginación).

**POST** `/todos` → Crear una nueva tarea.

**PUT** `/todos/{id}` → Actualizar una tarea (solo si pertenece al usuario autenticado).

**DELETE** `/todos/{id}` → Eliminar una tarea (solo si pertenece al usuario autenticado).

Para realizar peticiones autenticadas, incluye el token JWT en el encabezado:

``` bash
Authorization: Bearer <TOKEN>
```
---
# Requisitos para ejecutar
Antes de ejecutar el proyecto, asegúrate de tener instalado:

- **Java 21**
- **Maven**
- **PostgreSQL**
- **Git** (para clonar el repositorio)
- **Docker y Docker Compose** (Opcional, recomendado para una instalación más rápida)

## Instalación y ejecución

### 1. Clonar el repositorio
Ejecuta el siguiente comando en una terminal:
```bash
git clone https://github.com/tu-usuario/todo-list-api.git
cd todo-list-api
```

### 2. Configurar base de datos
Asegúrate de que PostgreSQL esté en ejecución y con la base de datos `todo_project`; para crearla puedas usar la siguiente consulta:
```sql
CREATE DATABASE todo_project;
```
O si usas Docker, ejecuta:
```bash
docker compose up -d
```

### 3. Configurar el archivo `application.properties`
Modifica `src/main/resources/application.properties` con tus credenciales de PostgreSQL:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/todo_project
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
```

### 4. Construir y ejecutar la aplicación
Desde la terminal en la carpeta del proyecto, ejecuta:
```bash
mvn clean install
mvn spring-boot:run
```

Si usas Docker:
```bash
docker compose up -d
```

### 5. Probar la API
Puedes probar los endpoints con **Postman**.

### **5.1. Registro de usuario**
- **Método:** `POST`
- **URL:** `http://localhost:8080/users/register`
- **Body (JSON):**
```json
{
  "name": "Juan Perez",
  "email": "juan@gmail.com",
  "password": "123456"
}
```

### **5.2. Inicio de sesión**
- **Método:** `POST`
- **URL:** `http://localhost:8080/users/login`
- **Body (JSON):**
```json
{
  "email": "juan@gmail.com",
  "password": "123456"
}
```
- **Respuesta esperada:** Un token JWT.
> **Nota**: copia el token recibido, ya que lo necesitarás para probar los demás Endpoints. Para usarlo debes dirigirte a la pestaña **Headers** para agregar en el campo **Key** la palabra Authorization y en el campo **Value** la palabra Bearer seguido del TOKEN, de la siguiente forma:
> ```bash
> Authorization: Bearer <TOKEN>
> 

### **5.3. Crear una tarea**
- **Método:** `POST`
- **URL:** `http://localhost:8080/todos`
- **Headers:**
  - `Authorization: Bearer <TOKEN>`
- **Body (JSON):**
```json
{
  "title": "Comprar despensa",
  "description": "Leche, huevos, arroz, panela y frutas"
}
```

### **5.4. Obtener todas las tareas**
- **Método:** `GET`
- **URL:** `http://localhost:8080/todos`
- **Headers:**
  - `Authorization: Bearer <TOKEN>`

### **5.5. Actualizar una tarea**
- **Método:** `PUT`
- **URL:** `http://localhost:8080/todos/{id}`
- **Headers:**
  - `Authorization: Bearer <TOKEN>`
- **Body (JSON):**
```json
{
  "title": "Completar despensa",
  "description": "Cereales, vegetales y carne"
}
```

### **5.6. Eliminar una tarea**
- **Método:** `DELETE`
- **URL:** `http://localhost:8080/todos/{id}`
- **Headers:**
  - `Authorization: Bearer <TOKEN>`

## 6. Despliegue con Docker 🐳

Si deseas ejecutar la API sin instalar dependencias, puedes usar Docker:

```bash
docker build -t todo-list-api .
docker run -p 8080:8080 todo-list-api
```
O con Docker Compose:
- Crea un archivo `docker-compose.yml` con PostgreSQL y la API.
- Ejecutar el comando:

``` bash
docker compose up -d
```





---
🚀 **Proyecto desarrollado por Juan Esteban Celada Bolivar**

