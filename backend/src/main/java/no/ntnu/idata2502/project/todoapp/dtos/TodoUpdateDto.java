package no.ntnu.idata2502.project.todoapp.dtos;

/**
 * The TodoUpdateDto class represents the data transfer object (DTO) for updating todos.
 * 
 * @author Candidate 10006
 * @version v1.1.0 (2025.04.30)
 */
public class TodoUpdateDto {
  private boolean complete;

  /**
   * Constructor for the TodoUpdateDto class.
   * 
   * @param complete The specified completion status
   */
  public TodoUpdateDto(boolean complete) {
    this.complete = complete;
  }

  /**
   * Getter for completion status.
   * 
   * @return Completion status
   */
  public boolean isComplete() {
    return this.complete;
  }
}
