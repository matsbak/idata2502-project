package no.ntnu.idata2502.project.todoapp.entites;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

/**
 * The TodoEntity class represents the entity class for the todo entity.
 * 
 * <p>The class uses JPA with annotations for ORM operations.</p>
 * 
 * @author Candidate 10006
 * @version v1.1.0 (2024.11.27)
 */
@Entity(name = "todo")
@Schema(description = "Todo entity, representing a specific todo")
public class TodoEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Schema(description = "Unique ID")
  private Long id;

  @Schema(description = "Todo description")
  private String description;

  @Schema(description = "Is todo completed or not")
  private boolean completed;

  @ManyToOne
  @JsonIgnore
  private ListEntity list;

  /**
   * Constructs an instance of the TodoEntity class.
   * 
   * <p>Empty constructor required by JPA.</p>
   */
  public TodoEntity() {
    // Intentionally left blank
  }

  /**
   * Constructs an instance of the TodoEntity class.
   * 
   * @param description The specified description
   */
  public TodoEntity(String description) {
    this.description = description;
    this.completed = false;
  }

  public Long getId() {
    return this.id;
  }

  public String getDescription() {
    return this.description;
  }

  public boolean isCompleted() {
    return this.completed;
  }

  public void setCompleted(boolean completed) {
    this.completed = completed;
  }

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
