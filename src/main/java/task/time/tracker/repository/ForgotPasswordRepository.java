package task.time.tracker.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import task.time.tracker.model.ForgotPassword;

@Repository
public interface ForgotPasswordRepository extends JpaRepository<ForgotPassword, Long> {
	Optional<ForgotPassword> getByTokenOrderByIdDesc(String token);
}
