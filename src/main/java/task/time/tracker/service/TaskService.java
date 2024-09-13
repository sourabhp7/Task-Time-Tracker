package task.time.tracker.service;

import task.time.tracker.dto.TaskDTO;

public interface TaskService {

	public void save(TaskDTO taskDTO,String operation);
}
