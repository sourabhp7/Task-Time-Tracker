package task.time.tracker.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import task.time.tracker.model.PermissionEntity;

@Repository
public interface PemissionEntityRepository extends JpaRepository<PermissionEntity, Long> {

	Optional<PermissionEntity> findByPermissonNameContainingIgnoreCase(String permissionName);

}
