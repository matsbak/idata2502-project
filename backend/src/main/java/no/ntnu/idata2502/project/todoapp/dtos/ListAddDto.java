package no.ntnu.idata2502.project.todoapp.dtos;

/**
 * The ListAddDto represents the data transfer object (DTO) for adding lists.
 * 
 * @author Candidate 10006
 * @version v1.1.0 (2025.04.30)
 */
public class ListAddDto {
  private String title;

  /**
   * Constructor for the ListAddDto class.
   * 
   * @param title The specified title
   */
  public ListAddDto(String title) {
    this.title = title;
  }

  /**
   * Getter for title.
   * 
   * @return Title
   */
  public String getTitle() {
    return this.title;
  }
}
