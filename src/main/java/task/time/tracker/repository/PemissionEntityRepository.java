package task.time.tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import task.time.tracker.model.PermissionEntity;

@Repository
public interface PemissionEntityRepository extends JpaRepository<PermissionEntity, Long> {

}
