package task.time.tracker.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import task.time.tracker.dto.PriorityDTO;
import task.time.tracker.model.Priority;
import task.time.tracker.repository.PriorityRepository;
import task.time.tracker.transformation.PriorityTransformation;

@Service
public class PriorityServiceImpl implements PriorityService {

	@Autowired
	private PriorityRepository priorityRepository;

	@Autowired
	private PriorityTransformation priorityTransformation;

	@Override
	public List<PriorityDTO> findAll() {
		List<PriorityDTO> priorityDTOs = new ArrayList<>();
		List<Priority> priorities = priorityRepository.findAll();
		if (!priorities.isEmpty()) {
			priorities.stream().forEach(priority -> priorityDTOs.add(priorityTransformation.entityToObject(priority)));
		}
		return priorityDTOs;
	}

	@Override
	public void save(PriorityDTO priorityDTO, String operation) {
		Priority priority = priorityTransformation.DtoToEntity(priorityDTO, operation);
		priorityRepository.save(priority);
	}

	@Override
	public void delete(Long id) {

	}

}
