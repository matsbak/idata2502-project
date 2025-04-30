package no.ntnu.idata2502.project.todoapp.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import no.ntnu.idata2502.project.todoapp.entites.TodoEntity;

/**
 * The TodoRepository interface represents the repository for {@link TodoEntity todos}.
 * 
 * @author Candidate 10006
 * @version v1.3.0 (2025.04.30)
 */
@Repository
public interface TodoRepository extends CrudRepository<TodoEntity, Long> {
}
