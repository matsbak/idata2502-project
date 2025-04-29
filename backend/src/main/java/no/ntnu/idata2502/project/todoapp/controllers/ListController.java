package no.ntnu.idata2502.project.todoapp.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import no.ntnu.idata2502.project.todoapp.entites.ListEntity;
import no.ntnu.idata2502.project.todoapp.services.ListService;

/**
 * The ListController class represents the REST controller for {@link ListEntity lists}. The class
 * handles all HTTP traffic affiliated with lists.
 * 
 * @author Candidate 10006
 * @version v1.1.0 (2025.04.29)
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
    logger.info("Sending all lists...");
    Iterable<ListEntity> lists = listService.getAll();
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
  public ResponseEntity<?> add(
    @Parameter(description = "Title of list to add")
    @RequestBody String title
  ) {
    ResponseEntity<?> response;
    try {
      ListEntity list = new ListEntity(title);
      listService.add(list);
      logger.info("Valid list, sending generated ID of created list...");
      // TODO No URI specified
      response = ResponseEntity.created(null).body(list.getId());
    } catch (IllegalArgumentException e) {
      logger.info("Invalid list, sending error message...");
      response = ResponseEntity.badRequest().body(e.getMessage());
    }
    return response;
  }

  /**
   * Endpoint for deleting the list with the specified ID.
   * 
   * @param id The specified ID
   * @return <p><b>200 OK</b> If list exists</p>
   *         <li><p><b>404 NOT FOUND</b> If list does not exist</p></li>
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
  public ResponseEntity<?> delete(@PathVariable Long id) {
    ResponseEntity<?> response;
    if (listService.delete(id)) {
      logger.info("List exists, sending error response...");
      response = ResponseEntity.ok().build();
    } else {
      logger.error("List does not exist, sending error response...");
      response = ResponseEntity.notFound().build();
    }
    return response;
  }
}
