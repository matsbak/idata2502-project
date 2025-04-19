package no.ntnu.idata2502.project.todoapp.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import no.ntnu.idata2502.project.todoapp.entites.TodoEntity;

/**
 * The TodoRepository interface represents the repository class for the {@link TodoEntity todo
 * entity}.
 * 
 * @author Candidate 10006
 * @version v1.0.0 (2024.11.26)
 */
@Repository
public interface TodoRepository extends CrudRepository<TodoEntity, Long> {
}
