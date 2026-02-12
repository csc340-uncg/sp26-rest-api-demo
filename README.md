# sp26-rest-api-demo

Demo REST API

## Description

Simple Spring Boot application that demonstrates how to create a REST API. It includes examples of GET, POST, PUT, and DELETE endpoints.

## Technologies Used

- Java 25
- Spring Boot
- Maven
- Jackson for JSON processing

## Installation

1. Clone the repository:
   ```
   git clone https://github.com/csc340-uncg/sp26-rest-api-demo.git
   ```
2. Open the project in your IDE (e.g., IntelliJ, Eclipse).
3. Build the project using Maven:
   ```
   mvn clean install
   ```
4. Run the application:
   ```
    mvn spring-boot:run
   ```

## API Endpoints

- `GET /api/hello`: Returns a greeting message.
- `GET /api/students`: Retrieves a list of all students.
- `GET /api/students/{id}`: Retrieves a student by ID.
- `GET /api/students/search?major={major}&minGpa={minGpa}`: Searches for students by major and minimum GPA.
- `POST /api/students`: Adds a new student.
- `PUT /api/students/{id}`: Updates an existing student.
- `DELETE /api/students/{id}`: Deletes a student.
- `GET /api/fruit?name={name}`: Fetches data from an external API (FruityVice).

## Testing
You can test the API endpoints using tools like Postman. For example, to add a new student, send a POST request to `http://localhost:8080/api/students` with the following JSON body:
```json
{
  "id": 1,
  "name": "John Doe",
  "email": "jd@school.edu",
  "major": "CSC",
  "gpa": 3.5
}
```

