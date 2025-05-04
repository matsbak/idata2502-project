package no.ntnu.idata2502.project.todoapp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

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

import no.ntnu.idata2502.project.todoapp.controllers.ListController;
import no.ntnu.idata2502.project.todoapp.dtos.ListAddDto;
import no.ntnu.idata2502.project.todoapp.entites.ListEntity;

/**
 * The ListControllerIntegrationTest class represents the integration test class for the
 * ListController class. The test class contains positive and negative tests of the list
 * controller.
 * 
 * <p>The class tests the endpoints in the controller in a production environment, meaning that the
 * actual communication between the application and the storage is tested here. This is different
 * from tests in other test classes, that set up custom testing environments to test their
 * respective classes.</p>
 * 
 * @author Candidate 10006
 * @version v1.1.0 (2025.05.04)
 * @see ListController
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ListControllerIntegrationTest {

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
    url = "http://localhost:" + port + "/api/lists";

    jdbcTemplate.execute("INSERT INTO list (list_id, title) VALUES (1, 'Groceries')");
    jdbcTemplate.execute("INSERT INTO list (list_id, title) VALUES (2, 'Chores')");
    jdbcTemplate.execute("INSERT INTO list (list_id, title) VALUES (3, 'Homework')");
  }

  /**
   * Tear down the production environment. This ensures that each test is idempotent.
   */
  @AfterEach
  public void tearDown() {
    jdbcTemplate.execute("DELETE FROM list");
  }

  /**
   * <b>Positive test</b>
   * 
   * <p>Test the endpoint for getting all lists.</p>
   * 
   * <p><code>[GET] /api/lists</code></p>
   */
  @Test
  @Order(1)
  public void testGetLists() {
    ResponseEntity<Iterable<ListEntity>> response = restTemplate.exchange(
      url,
      HttpMethod.GET,
      null,
      new ParameterizedTypeReference<Iterable<ListEntity>>() {}
    );

    assertEquals(HttpStatus.OK, response.getStatusCode());
    
    Iterable<ListEntity> body = response.getBody();
    assertNotNull(body);

    List<ListEntity> lists = new ArrayList<>();
    body.forEach(lists::add);

    assertEquals(3, lists.size());

    ListEntity list = lists.get(0);
    assertEquals(1, list.getId());
    assertEquals("Groceries", list.getTitle());
  }

  /**
   * <b>Positive test</b>
   * 
   * <p>Test the endpoint for adding a list. The endpoint accepts a title specified in the request
   * body, which is valid in this test.</p>
   * 
   * <p><code>[POST] /api/lists</code></p>
   */
  @Test
  @Order(2)
  public void testAddListWithValidRequestBody() {
    ListAddDto dto = new ListAddDto("Project");
    HttpEntity<ListAddDto> request = new HttpEntity<>(dto);

    ResponseEntity<Long> response = restTemplate.exchange(
      url,
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
   * <p>Test the endpoint for adding a list. The endpoint accepts a title specified in the request
   * body, which is <code>null</code> in this test.</p>
   * 
   * <p><code>[POST] /api/lists</code></p>
   */
  @Test
  @Order(3)
  public void testAddListWithNullRequestBody() {
    ListAddDto dto = new ListAddDto(null);
    HttpEntity<ListAddDto> request = new HttpEntity<>(dto);

    ResponseEntity<String> response = restTemplate.exchange(
      url,
      HttpMethod.POST,
      request,
      new ParameterizedTypeReference<String>() {}
    );

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

    String body = response.getBody();
    assertNotNull(body);
    assertEquals("The specified list is invalid", body);
  }

  /**
   * <b>Negative test</b>
   * 
   * <p>Test the endpoint for adding a list. The endpoint accepts a title specified in the request
   * body, which is empty in this test.</p>
   * 
   * <p><code>[POST] /api/lists</code></p>
   */
  @Test
  @Order(4)
  public void testAddListWithEmptyRequestBody() {
    ListAddDto dto = new ListAddDto("");
    HttpEntity<ListAddDto> request = new HttpEntity<>(dto);

    ResponseEntity<String> response = restTemplate.exchange(
      url,
      HttpMethod.POST,
      request,
      new ParameterizedTypeReference<String>() {}
    );

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

    String body = response.getBody();
    assertNotNull(body);
    assertEquals("The specified list is invalid", body);
  }

  /**
   * <b>Negative test</b>
   * 
   * <p>Test the endpoint for adding a list. The endpoint accepts a title specified in the request
   * body, which is just white space in this test.</p>
   * 
   * <p><code>[POST] /api/lists</code></p>
   */
  @Test
  @Order(5)
  public void testAddListWithWhiteSpaceRequestBody() {
    ListAddDto dto = new ListAddDto(" ");
    HttpEntity<ListAddDto> request = new HttpEntity<>(dto);

    ResponseEntity<String> response = restTemplate.exchange(
      url,
      HttpMethod.POST,
      request,
      new ParameterizedTypeReference<String>() {}
    );

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

    String body = response.getBody();
    assertNotNull(body);
    assertEquals("The specified list is invalid", body);
  }

  /**
   * <b>Positive test</b>
   * 
   * <p>Tests the endpoint for deleting a list. The endpoint accepts an ID specified as a path
   * variable, which is valid in this test.</p>
   * 
   * <p><code>[DELETE] /api/lists/{id}</code></p>
   */
  @Test
  @Order(6)
  public void testDeleteListWithValidPathVariable() {
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
   * <p>Tests the endpoint for deleting a list. The endpoint accepts an ID specified as a path
   * variable, which is invalid in this test.</p>
   * 
   * <p><code>[DELETE] /api/lists/{id}</code></p>
   */
  @Test
  @Order(7)
  public void testDeleteListWithInvalidPathVariable() {
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
   * <p>Tests the endpoint for deleting a list. The endpoint accepts an ID specified as a path
   * variable, which is on an invalid format in this test.</p>
   * 
   * <p><code>[DELETE] /api/lists/{id}</code></p>
   */
  @Test
  @Order(8)
  public void testDeleteListWithInvalidRequestFormatting() {
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
