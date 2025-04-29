package no.ntnu.idata2502.project.todoapp.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import no.ntnu.idata2502.project.todoapp.entites.ListEntity;
import no.ntnu.idata2502.project.todoapp.services.ListService;

/**
 * The ListController class represents the REST controller for {@link ListEntity lists}. The class
 * handles all HTTP traffic reaching its endpoints.
 * 
 * @author Candidate 10006
 * @version v1.1.3 (2025.04.29)
 */
@RestController
@RequestMapping("/api/lists")
public class ListController {

  @Autowired
  private ListService listService;

  private final Logger logger = LoggerFactory.getLogger(ListController.class);

  /**
   * Endpoint for getting all lists.
   * 
   * @return <p><b>200 OK</b> (<i>body:</i> all lists)</p>
   */
  @Operation(
    summary = "Get lists",
    description = "Gets all lists"
  )
  @ApiResponses(value = {
    @ApiResponse(
      responseCode = "200",
      description = "Signals success and contains all lists"
    )
  })
  @GetMapping
  public Iterable<ListEntity> getAll() {
    this.logger.info("[GET] Sending all lists...");
    Iterable<ListEntity> lists = this.listService.getAll();
    return lists;
  }

  /**
   * Endpoint for adding a list with the specified title.
   * 
   * @param title The specified title
   * @return <p><b>201 CREATED</b> if list is valid (<i>body:</i> generated ID of created list)</p>
   *         <li><p><b>400 BAD REQUEST</b> if list is invalid (<i>body:</i> error message)</p></li>
   */
  @Operation(
    summary = "Add list",
    description = "Adds a list with the specified title"
  )
  @ApiResponses(value = {
    @ApiResponse(
      responseCode = "201",
      description = "Signals success and contains generated ID of created list"
    ),
    @ApiResponse(
      responseCode = "400",
      description = "Signals error and contains error message"
    )
  })
  @PostMapping
  public ResponseEntity<Object> add(
    @Parameter(description = "Title of list to add")
    @RequestBody String title
  ) {
    ResponseEntity<Object> response;
    try {
      ListEntity list = new ListEntity(title);
      Long id = this.listService.add(list);
      this.logger.info("[POST] Valid list, sending generated ID of created list...");
      // TODO No URI specified
      response = ResponseEntity.created(null).body(id);
    } catch (IllegalArgumentException e) {
      this.logger.info("[POST] Invalid list, sending error message...");
      response = ResponseEntity.badRequest().body(e.getMessage());
    }
    return response;
  }

  /**
   * Endpoint for deleting the list with the specified ID.
   * 
   * @param id The specified ID
   * @return <p><b>200 OK</b> if list exists</p>
   *         <li><p><b>404 NOT FOUND</b> if list does not exist</p></li>
   */
  @Operation(
    summary = "Delete list",
    description = "Deletes the list with the specified ID"
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
    @Parameter(description = "ID of list to delete")
    @PathVariable Long id
  ) {
    ResponseEntity<Void> response;
    if (this.listService.delete(id)) {
      this.logger.info("[DELETE] List exists, sending success response...");
      response = ResponseEntity.ok().build();
    } else {
      this.logger.error("[DELETE] List does not exist, sending error response...");
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
