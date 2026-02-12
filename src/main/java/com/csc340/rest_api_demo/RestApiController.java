package com.csc340.rest_api_demo;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api")
public class RestApiController {

  Logger logger = Logger.getLogger(RestApiController.class.getName());

  Map<Long, Student> studentDatabase = new HashMap<>();

  /**
   * Hello World API endpoint.
   *
   * @return response string.
   */
  @GetMapping("/hello")
  public String hello() {
    return "Hello, World!";
  }

  /**
   * Get all students in the database. This will return a JSON array of student
   * objects.
   *
   * @return response entity containing the list of students.
   */
  @GetMapping("/students")
  public ResponseEntity<Collection<Student>> getAllStudents() {
    return ResponseEntity.ok(studentDatabase.values());
  }

  /**
   * Get a student by their ID. This will return a JSON object representing the
   * student with the specified ID. If no student with that ID exists, it will
   * return a 404 Not Found response.
   *
   * @param id
   * @return response entity containing the student object or a 404 Not Found
   *         response.
   */
  @GetMapping("/students/{id}")
  public ResponseEntity<Student> getStudentById(@PathVariable("id") Long id) {
    Student student = studentDatabase.get(id);
    if (student != null) {
      return ResponseEntity.ok(student);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  /**
   * Add a new student to the database. The student information will be passed as
   * a JSON object in the request body. The JSON should have the following format:
   *
   * <pre>
  {
    "id":12345,
    "name":"John Doe",
    "email":null,
    "major":"CSC",
    "gpa":3.5
  }
   * </pre>
   *
   * @param student
   * @return response entity containing the added student object or a 400 Bad
   *         Request response if a student with the same ID already exists.
   */
  @PostMapping("/students")
  public ResponseEntity<Student> addStudent(@RequestBody Student student) {
    Long id = student.getId();
    if (studentDatabase.containsKey(id)) {
      return ResponseEntity.badRequest().build();
    }
    studentDatabase.put(id, student);
    return ResponseEntity.ok(student);
  }

  /**
   * Update an existing student's information. The student ID will be passed as a
   * path variable,
   * and the updated student information will be passed as a JSON object in the
   * request body.
   * The JSON should have the same format as the one used for adding a student.
   * If no student with the specified ID exists, it will return a 404 Not Found
   * response.
   *
   * @param updatedStudent
   * @return response entity containing the updated student object or a 404 Not
   *         Found response if no student with the specified ID exists.
   */
  @PutMapping("/students/{id}")
  public ResponseEntity<Student> updateStudent(@PathVariable("id") Long id, @RequestBody Student updatedStudent) {
    if (studentDatabase.containsKey(id)) {
      studentDatabase.put(id, updatedStudent);
      return ResponseEntity.ok(updatedStudent);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  /**
   * Delete a student from the database. The student ID will be passed as a path
   * variable.
   * If no student with the specified ID exists, it will return a 404 Not Found
   * response.
   *
   * @param id
   * @return response entity with a 200 OK status if the student was deleted
   *         successfully or a 404 Not Found response if no student with the
   *         specified ID exists.
   */
  @DeleteMapping("/students/{id}")
  public ResponseEntity<Void> deleteStudent(@PathVariable("id") Long id) {
    if (studentDatabase.containsKey(id)) {
      studentDatabase.remove(id);
      return ResponseEntity.ok().build();
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  /**
   * Search for students based on their major and/or minimum GPA.The major and
   * minGpa parameters are optional query parameters. If major is provided, it
   * will filter students by their major.
   * If minGpa is provided, it will filter students by their GPA. If both
   * parameters are provided, it will return students that match both criteria.
   * If neither parameter is provided, it will return all students.
   *
   * @param major
   * @param minGpa
   * @return response entity containing the list of students that match the search
   *         criteria.
   */
  @GetMapping("/students/search")
  public ResponseEntity<Collection<Student>> searchStudents(@RequestParam(required = false) String major,
      @RequestParam(required = false) Double minGpa) {
    Collection<Student> results = studentDatabase.values().stream()
        .filter(student -> (major == null || student.getMajor().equals(major)) &&
            (minGpa == null || student.getGpa() >= minGpa))
        .toList();
    return ResponseEntity.ok(results);
  }

  /**
   * Fetch fruit information from the FruityVice API. The fruit name will be
   * passed as a query parameter.
   *
   * @param name
   * @return response entity containing a string with the fruit information or a
   *         500 Internal Server Error response if there was an error fetching the
   *         fruit information.
   */
  @GetMapping("/fruit")
  public ResponseEntity<String> getFruitInfo(@RequestParam String name) {
    String url = "https://www.fruityvice.com/api/fruit/" + name;
    RestTemplate restTemplate = new RestTemplate();
    ObjectMapper mapper = new ObjectMapper();

    /**
     * The response from the FruityVice API will be a JSON object that looks like
     * this:
     *
     * <pre>
      *  {
      *     "name": "Kiwi",
      *     "id": 66,
      *     "family": "Actinidiaceae",
      *     "order": "Struthioniformes",
      *     "genus": "Apteryx",
      *     "nutritions": {
      *         "calories": 61,
      *         "fat": 0.5,
      *         "sugar": 9.0,
      *         "carbohydrates": 15.0,
      *         "protein": 1.1
      *      }
      *  }
     * </pre>
     *
     * We will parse this JSON response to extract the fruit name, family, and
     * calories using the Jackson library.
     * We will then log this information and return a simple string response to the
     * client.
     */
    try {
      String response = restTemplate.getForObject(url, String.class);
      JsonNode jsonNode = mapper.readTree(response);
      String fruitName = jsonNode.get("name").asString();
      String family = jsonNode.get("family").asString();
      int calories = jsonNode.get("nutritions").get("calories").asInt();
      logger.info("Fetched fruit info: " + fruitName + ", Family: " + family + ", Calories: " + calories);

      return ResponseEntity.ok("Fruit: " + fruitName + ", Family: " + family);
    } catch (Exception e) {
      logger.severe("Error fetching fruit info: " + e.getMessage());
      return ResponseEntity.status(500).body("Error fetching fruit info");
    }

  }

}