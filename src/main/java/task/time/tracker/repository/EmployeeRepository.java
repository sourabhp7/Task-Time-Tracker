package task.time.tracker.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import task.time.tracker.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	
	Optional<Employee> findByResgistrationNoIgnoreCase(String resgistrationNo);

	@Query(value="SELECT count(*) from employee e where e.resgistration_no=?1 AND e.id=?2",nativeQuery = true)
	int isRegistrationNoExitsWhileUpdate(String resgistrationNo, Long id);

}
