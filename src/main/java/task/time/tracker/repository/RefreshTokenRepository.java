package task.time.tracker.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import task.time.tracker.model.RefreshToken;
import task.time.tracker.model.UserEntity;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

	int deleteByUserEntity(UserEntity userEntity);

	Optional<RefreshToken> findByToken(String token);

}
