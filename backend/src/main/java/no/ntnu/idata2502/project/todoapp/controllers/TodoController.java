package no.ntnu.idata2502.project.todoapp.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import io.swagger.v3.oas.annotations.Operation; 
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import no.ntnu.idata2502.project.todoapp.entites.TodoEntity;
import no.ntnu.idata2502.project.todoapp.services.TodoService;

/**
 * The TodoController class represents the REST controller class for {@link TodoEntity todos}. The
 * class handles all HTTP traffic reaching its endpoints.
 * 
 * @author Candidate 10006
 * @version v1.1.1 (2025.04.29)
 */
@CrossOrigin
@RestController
@RequestMapping("/api/todos")
public class TodoController {

  @Autowired
  private TodoService todoService;

  private final Logger logger = LoggerFactory.getLogger(TodoController.class);

  /**
   * Endpoint for getting all todos.
   * 
   * @return <p><b>200 OK</b> (<i>body:</i> all todos)</p>
   */
  @Operation(
    summary = "Get todos",
    description = "Gets all todos"
  )
  @ApiResponses(value = {
    @ApiResponse(
      responseCode = "200",
      description = "Signals success and contains all todos"
    )
  })
  @GetMapping
  public Iterable<TodoEntity> getAll() {
    logger.info("Sending all todos...");
    Iterable<TodoEntity> todos = this.todoService.getAll();
    return todos;
  }

  /**
   * Endpoint for adding a todo with the specified description.
   * 
   * @param description The specified description
   * @return <p><b>201 CREATED</b> if todo is valid (<i>body:</i> generated ID of created todo)</p>
   *         <li><p><b>400 BAD REQUEST</b> if todo is invalid (<i>body:</i> error message)</p></li>
   */
  @Operation(
    summary = "Add todo",
    description = "Adds the specified todo"
  )
  @ApiResponses(value = {
    @ApiResponse(
      responseCode = "201",
      description = "Signals success and contains generated ID of created todo"
    ),
    @ApiResponse(
      responseCode = "400",
      description = "Signals error and contains error message"
    )
  })
  @PostMapping
  public ResponseEntity<?> add(
    @Parameter(description = "Description of todo to add")
    @RequestBody String description
  ) {
    ResponseEntity<?> response;
    try {
      TodoEntity todo = new TodoEntity(description);
      Long id = this.todoService.add(todo);
      logger.info("Valid todo, sending generated ID of created todo...");
      // TODO No URI specified
      response = ResponseEntity.created(null).body(id);
    } catch (IllegalArgumentException e) {
      logger.error("Invalid todo, sending error message...");
      response = ResponseEntity.badRequest().body(e.getMessage());
    }
    return response;
  }

  /**
   * Endpoint for updating the todo with the specified ID with the specified completion status.
   * 
   * @param id The specified ID
   * @param completed The specified completion status
   * @return <p><b>200 OK</b> if todo exists</p>
   *         <li><p><b>404 NOT FOUND</b> if todo does not exist</p></li>
   */
  @Operation(
    summary = "Update todo",
    description = "Updates the todo with the specified ID with the specified completion status"
  )
  @ApiResponses(value = {
    @ApiResponse(
      responseCode = "200",
      description = "Signals success"
    ),
    @ApiResponse(
      responseCode = "404",
      description = "Signals error"
    )
  })
  @PutMapping("/{id}")
  public ResponseEntity<String> update(
    @Parameter(description = "ID of todo to update")
    @PathVariable Long id,
    @Parameter(description = "Updated completion status")
    @RequestBody boolean complete
  ) {
    ResponseEntity<String> response;
    if (this.todoService.update(id, complete)) {
      logger.info("Todo exists, sending success response...");
      response = ResponseEntity.ok().build();
    } else {
      logger.error("Todo does not exist, sending error response...");
      response = ResponseEntity.notFound().build();
    }
    return response;
  }

  /**
   * Endpoint for deleting the todo with the specified ID
   * 
   * @param id The specified ID
   * @return <p><b>200 OK</b> if todo exists</p>
   *         <li><b>404 NOT FOUND</b> if todo does not exist</p></li>
   */
  @Operation(
    summary = "Delete todo",
    description = "Deletes the todo with the specified ID"
  )
  @ApiResponses(value = {
    @ApiResponse(
      responseCode = "200",
      description = "Signals success"
    ),
    @ApiResponse(
      responseCode = "404",
      description = "Signals error"
    )
  })
  @DeleteMapping("/{id}")
  public ResponseEntity<String> delete(
    @Parameter(description = "ID of todo to delete")
    @PathVariable Long id
  ) {
    ResponseEntity<String> response;
    if (this.todoService.delete(id)) {
      logger.info("Todo exists, sending success response");
      response = ResponseEntity.ok().build();
    } else {
      logger.error("Todo does not exist, sending error response...");
      response = ResponseEntity.notFound().build();
    }
    return response;
  }

  /**
   * Exception handler for handling exceptions caused by invalid formatting of path variables. This
   * method sends a response to the request causing the specified exception.
   *
   * @param e The specified exception
   * @return <p><b>400 BAD REQUEST</b> (<i>body:</i> error message)<p>
   */
  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<String> handlePathVarException(MethodArgumentTypeMismatchException e) {
    logger.error("Received request contains invalid formatting, sending error message...");
    return ResponseEntity.badRequest().body(e.getMessage());
  }

  /**
   * Exception handler for handling exceptions caused by invalid formatting of request bodies. This
   * method sends a response to the request causing the specified exception.
   *
   * @param e The specified exception
   * @return <p><b>400 BAD REQUEST</b> (<i>body:</i> error message)</p>
   */
  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<String> handleRequestBodyException(HttpMessageNotReadableException e) {
    logger.error("Received request body contains invalid formatting, sending error message...");
    return ResponseEntity.badRequest().body(e.getMessage());
  }
}
