package no.ntnu.idata2502.project.todoapp.controllers;

import java.util.Optional;

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
import no.ntnu.idata2502.project.todoapp.entites.ListEntity;
import no.ntnu.idata2502.project.todoapp.entites.TodoEntity;
import no.ntnu.idata2502.project.todoapp.services.ListService;
import no.ntnu.idata2502.project.todoapp.services.TodoService;

/**
 * The TodoController class represents the REST controller class for {@link TodoEntity todos}. The
 * class handles all HTTP traffic reaching its endpoints.
 * 
 * @author Candidate 10006
 * @version v1.2.0 (2025.04.29)
 */
@CrossOrigin
@RestController
@RequestMapping("/api/todos")
public class TodoController {

  @Autowired
  private ListService listService;

  @Autowired
  private TodoService todoService;

  private final Logger logger = LoggerFactory.getLogger(TodoController.class);

  /**
   * Endpoint for getting all todos in the list with the specified list ID.
   * 
   * @param listId The specified list ID
   * @return <p><b>200 OK</b> (<i>body:</i> all todos in list)</p>
   *         <li><p><b>404 NOT FOUND</b> if list does not exist</p></li>
   */
  @Operation(
    summary = "Get todos in list",
    description = "Gets all todos in the list with the specified ID"
  )
  @ApiResponses(value = {
    @ApiResponse(
      responseCode = "200",
      description = "Signals success and contains all todos in list"
    ),
    @ApiResponse(
      responseCode = "404",
      description = "Signals error"
    )
  })
  @GetMapping("/{listId}")
  public ResponseEntity<Iterable<TodoEntity>> getList(@PathVariable Long listId) {
    ResponseEntity<Iterable<TodoEntity>> response;
    Optional<ListEntity> list = this.listService.get(listId);
    if (list.isPresent()) {
      Iterable<TodoEntity> todos = this.todoService.getList(listId);
      this.logger.info("[GET] Sending all todos in list...");
      response = ResponseEntity.ok().body(todos);
    } else {
      this.logger.error("[GET] List does not exist, sending error response...");
      response = ResponseEntity.notFound().build();
    }
    return response;
  }

  /**
   * Endpoint for adding a todo with the specified description.
   * 
   * @param description The specified description
   * @return <p><b>201 CREATED</b> if todo is valid (<i>body:</i> generated ID of created todo)</p>
   *         <li><p><b>400 BAD REQUEST</b> if todo is invalid (<i>body:</i> error message)</p></li>
   *         <li><p><b>404 NOT FOUND</b> if list does not exist</p></li>
   */
  @Operation(
    summary = "Add todo",
    description = "Adds a todo with the specified description"
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
  @PostMapping("/{listId}")
  public ResponseEntity<Object> add(
    @Parameter(description = "ID of list to add todo to")
    @PathVariable Long listId,
    @Parameter(description = "Description of todo to add")
    @RequestBody String description
  ) {
    ResponseEntity<Object> response;
    Optional<ListEntity> list = this.listService.get(listId);
    if (list.isPresent()) {
      try {
        TodoEntity todo = new TodoEntity(description);
        todo.setList(list.get());
        Long id = this.todoService.add(todo);
        this.logger.info("[POST] Valid todo, sending generated ID of created todo...");
        // TODO No URI specified
        response = ResponseEntity.created(null).body(id);
      } catch (IllegalArgumentException e) {
        this.logger.error("[POST] Invalid todo, sending error message...");
        response = ResponseEntity.badRequest().body(e.getMessage());
      }
    } else {
      this.logger.error("[POST] List does not exist, sending error response");
      response = ResponseEntity.notFound().build();
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
  public ResponseEntity<Void> update(
    @Parameter(description = "ID of todo to update")
    @PathVariable Long id,
    @Parameter(description = "Updated completion status")
    @RequestBody boolean complete
  ) {
    ResponseEntity<Void> response;
    if (this.todoService.update(id, complete)) {
      this.logger.info("[PUT] Todo exists, sending success response...");
      response = ResponseEntity.ok().build();
    } else {
      this.logger.error("[PUT] Todo does not exist, sending error response...");
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
  public ResponseEntity<Void> delete(
    @Parameter(description = "ID of todo to delete")
    @PathVariable Long id
  ) {
    ResponseEntity<Void> response;
    if (this.todoService.delete(id)) {
      this.logger.info("[DELETE] Todo exists, sending success response");
      response = ResponseEntity.ok().build();
    } else {
      this.logger.error("[DELETE] Todo does not exist, sending error response...");
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
    this.logger.error(
      "[EXCEPTION] Received request contains invalid formatting, sending error message..."
    );
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
    this.logger.error(
      "[EXCEPTION] Received request body contains invalid formatting, sending error message..."
    );
    return ResponseEntity.badRequest().body(e.getMessage());
  }
}
