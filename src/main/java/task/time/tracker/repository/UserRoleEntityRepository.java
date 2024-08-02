package task.time.tracker.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import task.time.tracker.model.UserEntity;
import task.time.tracker.model.UserRoleEntity;

@Repository
public interface UserRoleEntityRepository extends JpaRepository<UserRoleEntity, Long> {

	

	Set<UserRoleEntity> findByPkUserEntity(UserEntity userEntity);

}
