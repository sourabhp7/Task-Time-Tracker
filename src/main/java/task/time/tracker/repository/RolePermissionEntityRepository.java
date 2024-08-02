package task.time.tracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import task.time.tracker.model.RoleEntity;
import task.time.tracker.model.RolePermissionEntity;

@Repository
public interface RolePermissionEntityRepository extends JpaRepository<RolePermissionEntity, Long> {

	List<RolePermissionEntity> findByPkRole(RoleEntity roleEntity);
}
