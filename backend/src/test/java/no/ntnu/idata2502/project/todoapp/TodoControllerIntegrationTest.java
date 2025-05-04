package no.ntnu.idata2502.project.todoapp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;

import no.ntnu.idata2502.project.todoapp.controllers.TodoController;
import no.ntnu.idata2502.project.todoapp.dtos.TodoAddDto;
import no.ntnu.idata2502.project.todoapp.dtos.TodoUpdateDto;

/**
 * The TodoControllerIntegrationTest class represents the integration test class for the
 * TodoController class. The test class contains positive and negative tests of the todo
 * controller.
 * 
 * <p>The class tests the endpoints in the controller in a production environment, meaning that the
 * actual communication between the application and the storage is tested here. This is different
 * from tests in other test classes, that set up custom testing environments to test their
 * respective classes.</p>
 * 
 * @author Candidate 10006
 * @version v1.1.0 (2025.05.04)
 * @see TodoController
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TodoControllerIntegrationTest {

  @LocalServerPort
  public int port;

  @Autowired
  private TestRestTemplate restTemplate;

  @Autowired
  private JdbcTemplate jdbcTemplate;

  private String url;

  /**
   * Set up the production environment.
   */
  @BeforeEach
  public void setUp() {
    url = "http://localhost:" + port + "/api/todos";

    jdbcTemplate.execute(
      "INSERT INTO list (list_id, title) VALUES (1, 'Groceries')"
    );

    jdbcTemplate.execute(
      "INSERT INTO todo (todo_id, description, complete, list_id) VALUES (1, 'Milk', false, 1)"
    );
    jdbcTemplate.execute(
      "INSERT INTO todo (todo_id, description, complete, list_id) VALUES (2, 'Bread', false, 1)"
    );
    jdbcTemplate.execute(
      "INSERT INTO todo (todo_id, description, complete, list_id) VALUES (3, 'Butter', false, 1)"
    );
  }

  /**
   * Tear down the production environment. This ensures that each test is idempotent.
   */
  @AfterEach
  public void tearDown() {
    jdbcTemplate.execute("DELETE FROM todo");
    jdbcTemplate.execute("DELETE FROM list");
  }

  /**
   * <b>Positive test</b>
   * 
   * <p>Test the endpoint for adding a todo. The endpoint accepts a list ID specified as a path
   * variable and a description specified in the request body, which are valid in this test.</p>
   * 
   * <p><code>[POST] /api/todos/{listId}</code></p>
   */
  @Test
  @Order(1)
  public void testAddTodoWithValidRequest() {
    String testUrl = url + "/1";

    TodoAddDto dto = new TodoAddDto("Apple juice");
    HttpEntity<TodoAddDto> request = new HttpEntity<>(dto);

    ResponseEntity<Long> response = restTemplate.exchange(
      testUrl,
      HttpMethod.POST,
      request,
      new ParameterizedTypeReference<Long>() {}
    );

    assertEquals(HttpStatus.CREATED, response.getStatusCode());

    Long body = response.getBody();
    assertNotNull(body);
    assertTrue(body > 0);
  }

  /**
   * <b>Negative test</b>
   * 
   * <p>Test the endpoint for adding a todo. The endpoint accepts a list ID specified as a path
   * variable and a description specified in the request body, in which the list ID is invalid in
   * this test.</p>
   * 
   * <p><code>[POST] /api/todos/{listId}</code></p>
   */
  @Test
  @Order(2)
  public void testAddTodoWithInvalidPathVariable() {
    String testUrl = url + "/99";

    TodoAddDto dto = new TodoAddDto("Apple juice");
    HttpEntity<TodoAddDto> request = new HttpEntity<>(dto);

    ResponseEntity<Void> response = restTemplate.exchange(
      testUrl,
      HttpMethod.POST,
      request,
      new ParameterizedTypeReference<Void>() {}
    );

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }

  /**
   * <b>Negative test</b>
   * 
   * <p>Test the endpoint for adding a todo. The endpoint accepts a list ID specified as a path
   * variable and a description specified in the request body, in which the description is
   * <code>null</code> in this test.</p>
   * 
   * <p><code>[POST] /api/todos/{listId}</code></p>
   */
  @Test
  @Order(3)
  public void testAddTodoWithNullRequestBody() {
    String testUrl = url + "/1";

    TodoAddDto dto = new TodoAddDto(null);
    HttpEntity<TodoAddDto> request = new HttpEntity<>(dto);

    ResponseEntity<String> response = restTemplate.exchange(
      testUrl,
      HttpMethod.POST,
      request,
      new ParameterizedTypeReference<String>() {}
    );

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

    String body = response.getBody();
    assertNotNull(body);
    assertEquals("The specified todo is invalid", body);
  }

  /**
   * <b>Negative test</b>
   * 
   * <p>Test the endpoint for adding a todo. The endpoint accepts a list ID specified as a path
   * variable and a description specified in the request body, in which the description is empty in
   * this test.</p>
   * 
   * <p><code>[POST] /api/todos/{listId}</code></p>
   */
  @Test
  @Order(4)
  public void testAddTodoWithEmptyRequestBody() {
    String testUrl = url + "/1";

    TodoAddDto dto = new TodoAddDto("");
    HttpEntity<TodoAddDto> request = new HttpEntity<>(dto);

    ResponseEntity<String> response = restTemplate.exchange(
      testUrl,
      HttpMethod.POST,
      request,
      new ParameterizedTypeReference<String>() {}
    );

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

    String body = response.getBody();
    assertNotNull(body);
    assertEquals("The specified todo is invalid", body);
  }

  /**
   * <b>Negative test</b>
   * 
   * <p>Test the endpoint for adding a todo. The endpoint accepts a list ID specified as a path
   * variable and a description specified in the request body, in which the description is just
   * white space in this test.</p>
   * 
   * <p><code>[POST] /api/todos/{listId}</code></p>
   */
  @Test
  @Order(5)
  public void testAddTodoWithWhiteSpaceRequestBody() {
    String testUrl = url + "/1";

    TodoAddDto dto = new TodoAddDto(" ");
    HttpEntity<TodoAddDto> request = new HttpEntity<>(dto);

    ResponseEntity<String> response = restTemplate.exchange(
      testUrl,
      HttpMethod.POST,
      request,
      new ParameterizedTypeReference<String>() {}
    );

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

    String body = response.getBody();
    assertNotNull(body);
    assertEquals("The specified todo is invalid", body);
  }

  /**
   * <b>Negative test</b>
   * 
   * <p>Test the endpoint for adding a todo. The endpoint accepts a list ID specified as a path
   * variable and a description specified in the request body, in which the list ID is on an
   * invalid format in this test.</p>
   * 
   * <p><code>[POST] /api/todos/{listId}</code></p>
   */
  @Test
  @Order(6)
  public void testAddTodoWithInvalidRequestFormatting() {
    String testUrl = url + "/ninetynine";

    TodoAddDto dto = new TodoAddDto("Apple juice");
    HttpEntity<TodoAddDto> request = new HttpEntity<>(dto);

    ResponseEntity<String> response = restTemplate.exchange(
      testUrl,
      HttpMethod.POST,
      request,
      new ParameterizedTypeReference<String>() {}
    );

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertNotNull(response.getBody());
  }

  /**
   * <b>Positive test</b>
   * 
   * <p>Test the endpoint for updating a todo. The endpoint accepts an ID specified as a path
   * variable and a updated completion status specified in the request body, which are valid in
   * this test.</p>
   * 
   * <p><code>[PUT] /api/todos/{id}</code></p>
   */
  @Test
  @Order(7)
  public void testUpdateTodoWithValidRequest() {
    String testUrl = url + "/1";

    TodoUpdateDto dto = new TodoUpdateDto(true);
    HttpEntity<TodoUpdateDto> request = new HttpEntity<>(dto);

    ResponseEntity<Void> response = restTemplate.exchange(
      testUrl,
      HttpMethod.PUT,
      request,
      new ParameterizedTypeReference<Void>() {}
    );

    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  /**
   * <b>Negative test</b>
   * 
   * <p>Test the endpoint for updating a todo. The endpoint accepts an ID specified as a path
   * variable and a updated completion status specified in the request body, in which the ID is
   * invalid in this test.</p>
   * 
   * <p><code>[PUT] /api/todos/{id}</code></p>
   */
  @Test
  @Order(8)
  public void testUpdateTodoWithInvalidPathVariable() {
    String testUrl = url + "/99";

    TodoUpdateDto dto = new TodoUpdateDto(true);
    HttpEntity<TodoUpdateDto> request = new HttpEntity<>(dto);

    ResponseEntity<Void> response = restTemplate.exchange(
      testUrl,
      HttpMethod.PUT,
      request,
      new ParameterizedTypeReference<Void>() {}
    );

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }

  /**
   * <b>Negative test</b>
   * 
   * <p>Test the endpoint for updating a todo. The endpoint accepts an ID specified as a path
   * variable and a updated completion status specified in the request body, in which the ID is on
   * an invalid format in this test.</p>
   * 
   * <p><code>[PUT] /api/todos/{id}</code></p>
   */
  @Test
  @Order(9)
  public void testUpdateTodoWithInvalidRequestFormatting() {
    String testUrl = url + "/ninetynine";

    TodoUpdateDto dto = new TodoUpdateDto(true);
    HttpEntity<TodoUpdateDto> request = new HttpEntity<>(dto);

    ResponseEntity<String> response = restTemplate.exchange(
      testUrl,
      HttpMethod.PUT,
      request,
      new ParameterizedTypeReference<String>() {}
    );

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertNotNull(response.getBody());
  }

  /**
   * <b>Positive test</b>
   * 
   * <p>Test the endpoint for deleting a todo. The endpoint accepts a list ID specified as a path
   * variable, which is valid in this test.</p>
   * 
   * <p><code>[DELETE] /api/todos/{id}</code></p>
   */
  @Test
  @Order(10)
  public void testDeleteTodoWithValidPathVariable() {
    String testUrl = url + "/1";

    ResponseEntity<Void> response = restTemplate.exchange(
      testUrl,
      HttpMethod.DELETE,
      null,
      new ParameterizedTypeReference<Void>() {}
    );

    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  /**
   * <b>Negative test</b>
   * 
   * <p>Test the endpoint for deleting a todo. The endpoint accepts a list ID specified as a path
   * variable, which is invalid in this test.</p>
   * 
   * <p><code>[DELETE] /api/todos/{id}</code></p>
   */
  @Test
  @Order(11)
  public void testDeleteTodoWithInvalidPathVariable() {
    String testUrl = url + "/99";

    ResponseEntity<Void> response = restTemplate.exchange(
      testUrl,
      HttpMethod.DELETE,
      null,
      new ParameterizedTypeReference<Void>() {}
    );

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }

  /**
   * <b>Negative test</b>
   * 
   * <p>Test the endpoint for deleting a todo. The endpoint accepts a list ID specified as a path
   * variable, which is on an invalid format in this test.</p>
   * 
   * <p><code>[DELETE] /api/todos/{id}</code></p>
   */
  @Test
  @Order(12)
  public void testDeleteTodoWithInvalidRequestFormatting() {
    String testUrl = url + "/ninetynine";

    ResponseEntity<String> response = restTemplate.exchange(
      testUrl,
      HttpMethod.DELETE,
      null,
      new ParameterizedTypeReference<String>() {}
    );

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertNotNull(response.getBody());
  }
}
