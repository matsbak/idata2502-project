package no.ntnu.idata2502.project.todoapp.dto;

/**
 * The TodoUpdateDto class represents the data transfer object (DTO) for updating todo entites. The
 * request to update entites are sent from the frontend.
 * 
 * @author Candidate 10006
 * @version v1.0.0 (2024.11.27)
 */
public class TodoUpdateDto {
  private boolean completed;

  public TodoUpdateDto(boolean completed) {
    this.completed = completed;
  }

  public boolean isCompleted() {
    return this.completed;
  }
}
