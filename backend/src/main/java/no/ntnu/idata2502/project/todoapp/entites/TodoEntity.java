package no.ntnu.idata2502.project.todoapp.entites;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * The TodoEntity class represents a specific todo. The class contains JPA annotations for ORM
 * operations.
 * 
 * @author Candidate 10006
 * @version v1.2.0 (2025.04.29)
 */
@Entity
@Table(name = "todo")
@Schema(description = "Todo entity representing a specific todo")
public class TodoEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "todo_id")
  @Schema(description = "Unique ID")
  private Long id;

  @Column(name = "descripton")
  @Schema(description = "Todo description")
  private String description;

  @Column(name = "complete")
  @Schema(description = "Todo completion status")
  private boolean complete;

  @JsonIgnore
  @ManyToOne
  @JoinColumn(name = "list_id", nullable = false)
  @Schema(description = "List containing todo")
  private ListEntity list;

  /**
   * Default constructor required by JPA.
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
    this.complete = false;
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
  public boolean isComplete() {
    return this.complete;
  }

  /**
   * Setter for completion status.
   * 
   * @param completed The specified completion status
   */
  public void setCompleted(boolean complete) {
    this.complete = complete;
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
   * Setter for list.
   * 
   * @param list The specified list
   */
  public void setList(ListEntity list) {
    this.list = list;
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
