package task.time.tracker.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import task.time.tracker.model.Priority;
import task.time.tracker.model.RoleEntity;

@Repository
public interface PriorityRepository extends JpaRepository<Priority, Long> {
	Optional<Priority> findByNameIgnoreCase(String name);
}
