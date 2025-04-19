package no.ntnu.idata2502.project.todoapp.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import no.ntnu.idata2502.project.todoapp.dto.TodoAddDto;
import no.ntnu.idata2502.project.todoapp.dto.TodoUpdateDto;
import no.ntnu.idata2502.project.todoapp.entites.TodoEntity;
import no.ntnu.idata2502.project.todoapp.services.TodoService;

/**
 * The TodoController class represents the REST controller class for todos.
 * 
 * <p>All HTTP requests affiliated with todos are handled in this class.</p>
 * 
 * @author Candidate 10006
 * @version v1.0.0 (2024.11.26)
 */
@CrossOrigin
@RestController
@RequestMapping("/api/todos")
public class TodoController {

  @Autowired
  private TodoService todoService;

  private final Logger logger = LoggerFactory.getLogger(TodoController.class);

  /**
   * Returns an iterable containing all todos.
   * 
   * <p>The response body contains all todo data.</p>
   * 
   * @return 200 OK + all todo data
   */
  @Operation(
    summary = "Get all todos",
    description = "Gets all todos"
  )
  @ApiResponses(value = {
    @ApiResponse(
      responseCode = "200",
      description = "All todo data"
    )
  })
  @GetMapping
  public Iterable<TodoEntity> getAll() {
    logger.info("Sending all todo data...");
    Iterable<TodoEntity> todos = todoService.getAll();

    return todos;
  }

  /**
   * Returns a HTTP response to the request requesting to add the specified todo.
   * 
   * <p>The response body contains the generated ID of the specified todo on success or a string
   * with an error message on error.</p>
   * 
   * @param todo The specified todo
   * @return <p>201 CREATED on success + ID</p>
   *         <p>400 BAD REQUEST on error</p>
   */
  @Operation(
    summary = "Add todo",
    description = "Adds the specified todo"
  )
  @ApiResponses(value = {
    @ApiResponse(
      responseCode = "201",
      description = "ID of specified todo"
    ),
    @ApiResponse(
      responseCode = "400",
      description = "Error message"
    )
  })
  @PostMapping
  public ResponseEntity<?> add(
    @Parameter(description = "The description to add the todo with")
    @RequestBody TodoAddDto todoAdd
  ) {
    ResponseEntity<?> response;

    try {
      TodoEntity todo = new TodoEntity(todoAdd.getDescription());
      todoService.add(todo);

      logger.info("Sending generated ID of new todo...");
      response = new ResponseEntity<>(todo.getId(), HttpStatus.CREATED);
    } catch (IllegalArgumentException e) {
      logger.error("Invalid todo data, sending error message...");
      response = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    return response;
  }

  /**
   * Returns a HTTP response to the request requesting to update the todo with the specified ID
   * with the specified complete status.
   * 
   * <p>Since if a todo is complete or not should be the only data that can be modified in a todo,
   * this is the only data passed to this endpoint.</p>
   * 
   * <p>The response body contains an empty string on success or a string with an error message on
   * error.</p>
   * 
   * @param id The specified ID
   * @param completed The specified complete status
   * @return <p>200 OK on success</p>
   *         <p>404 NOT FOUND if todo is not found</p>
   */
  @Operation(
    summary = "Update todo",
    description = "Updates the todo with the specified ID with the specified complete status"
  )
  @ApiResponses(value = {
    @ApiResponse(
      responseCode = "200",
      description = "Empty string"
    ),
    @ApiResponse(
      responseCode = "404",
      description = "Error message"
    )
  })
  @PutMapping("/{id}")
  public ResponseEntity<String> update(
    @Parameter(description = "The ID of the todo to update")
    @PathVariable Long id,
    @Parameter(description = "The complete status to update the existing todo with")
    @RequestBody TodoUpdateDto todoUpdate
  ) {
    ResponseEntity<String> response;

    if (todoService.update(id, todoUpdate.isCompleted())) {
      logger.info("Todo found, updated todo");
      response = new ResponseEntity<>("", HttpStatus.OK);
    } else {
      logger.error("Todo not found, sending error message...");
      response = new ResponseEntity<>("Todo with specified ID not found", HttpStatus.NOT_FOUND);
    }

    return response;
  }

  /**
   * Returns a HTTP response to the request requesting to delete the todo with the specified ID.
   * 
   * <p>The response body contains an empty string on success or a string with an error message on
   * error.</p>
   * 
   * @param id The specified ID
   * @return <p>200 OK on success</p>
   *         <p>404 NOT FOUND if todo is not found</p>
   */
  @Operation(
    summary = "Delete todo",
    description = "Deletes the todo with the specified ID"
  )
  @ApiResponses(value = {
    @ApiResponse(
      responseCode = "200",
      description = "Empty string"
    ),
    @ApiResponse(
      responseCode = "404",
      description = "Error message"
    )
  })
  @DeleteMapping("/{id}")
  public ResponseEntity<String> delete(
    @Parameter(description = "The ID of the todo to delete")
    @PathVariable Long id
  ) {
    ResponseEntity<String> response;

    if (todoService.delete(id)) {
      logger.info("Todo found, deleted todo");
      response = new ResponseEntity<>("", HttpStatus.OK);
    } else {
      logger.error("Todo not found, sending error message...");
      response = new ResponseEntity<>("Todo with specified ID not found", HttpStatus.NOT_FOUND);
    }

    return response;
  }

  // Exception handling

  /**
   * Returns a HTTP response to the request causing the specified
   * MethodArgumentTypeMismatchException.
   *
   * @param e The specified MethodArgumentTypeMismatchException
   * @return 400 BAD REQUEST + error message
   */
  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<String> handlePathVarException(MethodArgumentTypeMismatchException e) {
    logger.error("Received HTTP request could not be read, sending error message...");
    return new ResponseEntity<>("HTTP request contains a value on an invalid format",
                                HttpStatus.BAD_REQUEST);
  }

  /**
   * Returns a HTTP response to the request causing the specified HttpMessageNotReadableException.
   *
   * @param e The specified HttpMessageNotReadableException
   * @return 400 BAD REQUEST + error message
   */
  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<String> handleRequestBodyException(HttpMessageNotReadableException e) {
    logger.error("Received data could not be read, sending error message...");
    return new ResponseEntity<>("Data not supplied or contains a parameter on an invalid format",
                                HttpStatus.BAD_REQUEST);
  }
}
