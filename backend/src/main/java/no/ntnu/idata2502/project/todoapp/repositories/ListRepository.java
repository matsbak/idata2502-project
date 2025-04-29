package no.ntnu.idata2502.project.todoapp.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import no.ntnu.idata2502.project.todoapp.entites.ListEntity;

/**
 * The ListRepository interface represents the repository for {@link ListEntity lists}.
 * 
 * @author Candidate 10006
 * @version v1.1.0 (2025.04.27)
 */
@Repository
public interface ListRepository extends CrudRepository<ListEntity, Long> {
}
