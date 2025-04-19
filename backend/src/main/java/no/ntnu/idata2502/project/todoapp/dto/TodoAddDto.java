package no.ntnu.idata2502.project.todoapp.dto;

/**
 * The TodoAddDto class represents the data transfer object (DTO) for adding todo entites. The
 * request to add entites are sent from the frontend.
 * 
 * @author Candidate 10006
 * @version v1.0.0 (2024.11.27)
 */
public class TodoAddDto {
  private String description;

  public TodoAddDto(String description) {
    this.description = description;
  }

  public String getDescription() {
    return this.description;
  }
}