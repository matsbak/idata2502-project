package no.ntnu.idata2502.project.todoapp.entites;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

/**
 * The TodoEntity class represents a specific todo. The class contains JPA annotations for ORM
 * operations.
 * 
 * @author Candidate 10006
 * @version v1.1.1 (2025.04.27)
 */
@Entity(name = "todo")
@Schema(description = "Todo entity representing a specific todo")
public class TodoEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Schema(description = "Unique ID")
  private Long id;

  @Schema(description = "Todo description")
  private String description;

  @Schema(description = "Completion status of todo")
  private boolean completed;

  @ManyToOne
  @JsonIgnore
  private ListEntity list;

  /**
   * Empty constructor required by JPA.
   */
  public TodoEntity() {
    // Intentionally left blank
  }

  /**
   * Constructor for the TodoEntity class.
   * 
   * @param description The specified description
   */
  public TodoEntity(String description) {
    this.description = description;
    this.completed = false;
  }

  /**
   * Getter for ID.
   * 
   * @return ID
   */
  public Long getId() {
    return this.id;
  }

  /**
   * Getter for description.
   * 
   * @return Description
   */
  public String getDescription() {
    return this.description;
  }

  /**
   * Getter for completion status.
   * 
   * @return Completion status
   */
  public boolean isCompleted() {
    return this.completed;
  }

  /**
   * Setter for completion status.
   * 
   * @param completed The specified completion status
   */
  public void setCompleted(boolean completed) {
    this.completed = completed;
  }

  /**
   * Getter for list.
   * 
   * @return List
   */
  public ListEntity getList() {
    return this.list;
  }

  /**
   * Returns true if the todo is valid or false otherwise.
   * 
   * @return True if the todo is valid or false otherwise
   */
  public boolean isValid() {
    return this.description != null && !this.description.isBlank();
  }
}
