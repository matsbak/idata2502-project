package no.ntnu.idata2502.project.todoapp.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import no.ntnu.idata2502.project.todoapp.entites.ListEntity;
import no.ntnu.idata2502.project.todoapp.repositories.ListRepository;

/**
 * The ListService class represents the service for {@link ListEntity lists}.
 * 
 * @author Candidate 10006
 * @version v1.1.1 (2025.04.29)
 */
@Service
public class ListService {

  @Autowired
  private ListRepository listRepository;

  /**
   * Gets all lists.
   * 
   * @return All lists
   */
  public Iterable<ListEntity> getAll() {
    return this.listRepository.findAll();
  }

  /**
   * Adds the specified list. The specified list is only added if it is valid.
   * 
   * @param list The specified list
   * @return The generated ID if the specified list is valid
   * @throws IllegalArgumentException If the specified list is invalid
   */
  public Long add(ListEntity list) {
    if (!list.isValid()) {
      throw new IllegalArgumentException("The specified list is invalid");
    }
    this.listRepository.save(list);
    return list.getId();
  }

  /**
   * Deletes the list with the specified ID. The list is only deleted if a list with the specified
   * ID exists.
   * 
   * @param id The specified ID
   * @return True if the list exists and is deleted or false otherwise
   */
  public boolean delete(Long id) {
    Optional<ListEntity> list = this.listRepository.findById(id);
    boolean exist = list.isPresent();
    if (exist) {
      this.listRepository.deleteById(id);
    }
    return exist;
  }
}
