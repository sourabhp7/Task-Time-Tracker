package task.time.tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import task.time.tracker.model.TaskFile;

public interface TaskFileRepository extends JpaRepository<TaskFile,Long> {

}
