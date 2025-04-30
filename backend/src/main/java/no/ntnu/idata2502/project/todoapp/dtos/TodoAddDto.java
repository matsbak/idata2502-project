package no.ntnu.idata2502.project.todoapp.dtos;

/**
 * The TodoAddDto class represents the data transfer object (DTO) for adding todos.
 * 
 * @author Candidate 10006
 * @version v1.1.0 (2025.04.30)
 */
public class TodoAddDto {
  private String description;

  /**
   * Constructor for the TodoAddDto class.
   * 
   * @param description The specified description
   */
  public TodoAddDto(String description) {
    this.description = description;
  }

  /**
   * Getter for description.
   * 
   * @return Description
   */
  public String getDescription() {
    return this.description;
  }
}
