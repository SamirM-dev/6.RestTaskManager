## Task Management System

A REST API application for managing tasks and their comments, supporting the creation, update (full and partial), deletion, and retrieval of tasks, with authentication.


## Technology Stack

- Spring Boot 4.1.0, Java 21
- Spring MVC
- Spring Data JPA
- PostgreSQL
- Bean Validation
- Swagger UI
- Lombok

## How to Get Started

Clone from the repository:
```
git clone https://github.com/SamirM-dev/6.RestTaskManager.git
```
Set the environment variables (database connection details):
```
DB_URL=jdbc:postgresql://...
DB_USERNAME=...
DB_PASSWORD=...
```
Build and run locally:
```
./mvnw package -DskipTests
java -jar target/taskmanager-0.0.1-SNAPSHOT.jar
```
Migrations (Flyway) and table creation are performed automatically on first run.
## Documentation

- Complete interactive documentation: http://localhost:8080/swagger-ui/index.html
- Postman collection: [postman/TaskManagementSystem.postman_collection.json](postman/TaskManagementSystem.postman_collection.json)
   - Import: Postman → Import → select the collection file
   - Create an environment variable `SERVERURL` with the value `http://localhost:8080/api/v1`
## Example Request
- Create a User:
```
curl -X ‘POST’ \
  ‘http://localhost:8080/api/v1/users’ \
  -H ‘accept: */*’ \
  -H ‘Content-Type: application/json’ \
  -d '{
  “name”: “string”,
  “email”: “user@example.com”,
  “password”: “stringst”
}'
```

- Search for tasks with pagination and filters:
```
curl -X ‘GET’ \
  ‘http://localhost:8080/api/v1/tasks?status=NEW&priority=MEDIUM&page=0&size=5&sort=id%2Cdesc’ \
  -H ‘accept: */*’
```
