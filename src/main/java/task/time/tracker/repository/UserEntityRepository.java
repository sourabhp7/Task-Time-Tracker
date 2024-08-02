package task.time.tracker.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import task.time.tracker.model.UserEntity;

@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {

	Optional<UserEntity> findByUserName(String userName);

}
