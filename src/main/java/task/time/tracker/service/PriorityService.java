package task.time.tracker.service;

import java.util.List;
import task.time.tracker.dto.PriorityDTO;

public interface PriorityService {

	public List<PriorityDTO> findAll();

	public void save(PriorityDTO priorityDTO, String operation);

	public void delete(Long id);
}
