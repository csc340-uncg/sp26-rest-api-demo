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
2. Open the project in your IDE (e.g., VSCode, IntelliJ, Eclipse).

3. Build and run the application. You can use the command line or your IDE's run configuration.

   - Using command line (make sure the JAVA_HOME environment variable is set to Java 25):
     ```
     ./mvnw spring-boot:run
     ```

   - Using IDE:
     - In IntelliJ, right-click on `RestApiDemoApplication.java` and select "Run".
     - In Eclipse, right-click on `RestApiDemoApplication.java`, select "Run As", and then "Java Application".
     - In VSCode, open the `RestApiDemoApplication.java` file and click on the "Run" icon above the main method.

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
