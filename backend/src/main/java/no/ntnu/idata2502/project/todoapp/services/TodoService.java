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
 * @version v1.1.1 (2025.04.27)
 */
@Service
public class TodoService {

  @Autowired
  private TodoRepository todoRepository;

  /**
   * Returns all todos.
   * 
   * @return All todos
   */
  public Iterable<TodoEntity> getAll() {
    return todoRepository.findAll();
  }

  /**
   * Returns the generated ID of the specified todo after it is stored. The todo is only stored if
   * it is valid.
   * 
   * @param todo The specified todo
   * @return The generated ID if the specified todo is valid
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
   * Returns true if the todo with the specified ID exists and is updated with the specified
   * completion status or false otherwise.
   * 
   * @param id The specified ID
   * @param completed The specified completion status
   * @return True if the todo exists and is updated or false otherwise
   */
  public boolean update(Long id, boolean completed) {
    Optional<TodoEntity> todo = todoRepository.findById(id);
    boolean exist = todo.isPresent();
    if (exist) {
      TodoEntity existingTodo = todo.get();
      existingTodo.setCompleted(completed);
      todoRepository.save(existingTodo);
    }
    return exist;
  }

  /**
   * Returns true if the todo with the specified ID exists and is deleted or false otherwise.
   * 
   * @param id The specified ID
   * @return True if the todo exists and is deleted or false otherwise
   */
  public boolean delete(Long id) {
    Optional<TodoEntity> todo = todoRepository.findById(id);
    boolean exist = todo.isPresent();
    if (exist) {
      todoRepository.deleteById(id);
    }
    return exist;
  }
}
