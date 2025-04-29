package no.ntnu.idata2502.project.todoapp.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import no.ntnu.idata2502.project.todoapp.entites.TodoEntity;
import no.ntnu.idata2502.project.todoapp.repositories.TodoRepository;

/**
 * The TodoService class represents the service for {@link TodoEntity todos}.
 * 
 * @author Candidate 10006
 * @version v1.2.0 (2025.04.29)
 */
@Service
public class TodoService {

  @Autowired
  private TodoRepository todoRepository;

  /**
   * Gets all todos within the list with the specified ID.
   * 
   * @return All todos the list
   */
  public Iterable<TodoEntity> getList(Long listId) {
    return this.todoRepository.findByListId(listId);
  }

  /**
   * Adds the specified todo. The specified todo is only added if it is valid.
   * 
   * @param todo The specified todo
   * @return The generated ID if the specified todo is valid
   * @throws IllegalArgumentException If the specified todo is invalid
   */
  public Long add(TodoEntity todo) {
    if (!todo.isValid()) {
      throw new IllegalArgumentException("The specified todo is invalid");
    }
    this.todoRepository.save(todo);
    return todo.getId();
  }

  /**
   * Updates the todo with the specified ID with the specified completion status. The todo is only
   * updated if a todo with the specified ID exits.
   * 
   * @param id The specified ID
   * @param complete The specified completion status
   * @return True if the todo exists and is updated or false otherwise
   */
  public boolean update(Long id, boolean complete) {
    Optional<TodoEntity> todo = this.todoRepository.findById(id);
    boolean exist = todo.isPresent();
    if (exist) {
      TodoEntity existingTodo = todo.get();
      existingTodo.setCompleted(complete);
      this.todoRepository.save(existingTodo);
    }
    return exist;
  }

  /**
   * Deletes the todo with the specified ID. The todo is only deleted if a todo with the specified
   * ID exists.
   * 
   * @param id The specified ID
   * @return True if the todo exists and is deleted or false otherwise
   */
  public boolean delete(Long id) {
    Optional<TodoEntity> todo = this.todoRepository.findById(id);
    boolean exist = todo.isPresent();
    if (exist) {
      this.todoRepository.deleteById(id);
    }
    return exist;
  }
}
