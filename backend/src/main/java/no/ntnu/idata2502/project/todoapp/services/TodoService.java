package no.ntnu.idata2502.project.todoapp.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import no.ntnu.idata2502.project.todoapp.entites.TodoEntity;
import no.ntnu.idata2502.project.todoapp.repositories.TodoRepository;

/**
 * The TodoService class represents the service class for the {@link TodoEntity todo entity}.
 * 
 * @author Candidate 10006
 * @version v1.1.0 (2024.11.27)
 */
@Service
public class TodoService {

  @Autowired
  private TodoRepository todoRepository;

  /**
   * Returns all todos in the database.
   * 
   * @return All todos
   */
  public Iterable<TodoEntity> getAll() {
    return todoRepository.findAll();
  }

  /**
   * Returns the generated ID of the specified todo if it is added to the database.
   * 
   * @param todo The specified todo
   * @return The generated ID if the todo is added
   * @throws IllegalArgumentException If the specified todo is invalid
   */
  public Long add(TodoEntity todo) {
    if (!todo.isValid()) {
      throw new IllegalArgumentException("Todo is invalid");
    }

    todoRepository.save(todo);

    return todo.getId();
  }

  /**
   * Returns true if the todo with the specified ID is found and updated with the specified
   * complete status or false otherwise.
   * 
   * @param id The specified ID
   * @param completed The specified complete status
   * @return True if the todo is found and updated or false otherwise
   */
  public boolean update(Long id, boolean completed) {
    Optional<TodoEntity> targetTodo = todoRepository.findById(id);
    if (targetTodo.isPresent()) {
      TodoEntity existingTodo = targetTodo.get();

      existingTodo.setCompleted(completed);

      todoRepository.save(existingTodo);
    }

    return targetTodo.isPresent();
  }

  /**
   * Returns true if the todo with the specified ID is found and deleted or false otherwise.
   * 
   * @param id The specified ID
   * @return True if the todo is found and deleted or false otherwise
   */
  public boolean delete(Long id) {
    Optional<TodoEntity> todo = todoRepository.findById(id);
    if (todo.isPresent()) {
      todoRepository.deleteById(id);
    }

    return todo.isPresent();
  }
}
