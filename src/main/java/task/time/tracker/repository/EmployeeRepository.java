package task.time.tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import task.time.tracker.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
