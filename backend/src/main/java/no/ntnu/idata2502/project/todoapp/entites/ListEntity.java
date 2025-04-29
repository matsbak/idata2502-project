package no.ntnu.idata2502.project.todoapp.entites;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * The ListEntity class represents a specific list of {@link TodoEntity todos}. The class contains
 * JPA annotations for ORM operations.
 * 
 * @author Candidate 10006
 * @version v1.1.1 (2025.04.29)
 */
@Entity
@Table(name = "list")
@Schema(description = "List entity representing a specific list of todos")
public class ListEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "list_id")
  @Schema(description = "Unique ID")
  private Long id;

  @Column(name = "title")
  @Schema(description = "List title")
  private String title;

  @OneToMany(mappedBy = "list")
  @Schema(description = "Todos in list")
  private List<TodoEntity> todos;

  /**
   * Default constructor required by JPA.
   */
  public ListEntity() {
    // Intentionally left blank
  }

  /**
   * Constructor for the ListEntity class.
   * 
   * @param title The specified title
   */
  public ListEntity(String title) {
    this.title = title;
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
   * Getter for title.
   * 
   * @return Title
   */
  public String getTitle() {
    return this.title;
  }

  /**
   * Getter for todos.
   * 
   * @return Todos
   */
  public List<TodoEntity> getTodos() {
    return this.todos;
  }

  /**
   * Returns true if the list is valid or false otherwise.
   * 
   * @return True if the list is valid or false otherwise
   */
  public boolean isValid() {
    return this.title != null && !this.title.isBlank();
  }
}
