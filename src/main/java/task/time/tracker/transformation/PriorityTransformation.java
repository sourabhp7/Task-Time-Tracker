package task.time.tracker.transformation;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import task.time.tracker.dto.PermissionRequest;
import task.time.tracker.dto.PriorityDTO;
import task.time.tracker.dto.PrivilageDTO;
import task.time.tracker.exception.ResourceNotFound;
import task.time.tracker.model.PermissionEntity;
import task.time.tracker.model.Priority;
import task.time.tracker.repository.PriorityRepository;

@Component
public class PriorityTransformation {

	@Autowired
	private PriorityRepository priorityRepository;

	public PriorityDTO entityToObject(Priority priority) {
		PriorityDTO priorityDTO = new PriorityDTO();
		priorityDTO.setId(priority.getId());
		priorityDTO.setName(priority.getName());
		return priorityDTO;
	}

	public Priority DtoToEntity(PriorityDTO priorityDTO, String Operation) {
		Priority priority = null;
		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		final String userName = auth.getName();
		if (task.time.tracker.constant.Operation.MODIFIED.value().equalsIgnoreCase(Operation)) {
			priority = priorityRepository.findById(priorityDTO.getId())
					.orElseThrow(() -> new ResourceNotFound("Permission Id Not Found"));
		}
		if (priority == null) {
			priority = new Priority();
		}
		priority.setName(priorityDTO.getName());
		if (task.time.tracker.constant.Operation.ADD.value().equalsIgnoreCase(Operation)) {
			priority.setCreatedAt(LocalDateTime.now());
			priority.setUpdatedAt(LocalDateTime.now());
			priority.setCreatedBy(userName);
			priority.setUpdatedBy(userName);
		} else if (task.time.tracker.constant.Operation.MODIFIED.value().equalsIgnoreCase(Operation)) {
			priority.setUpdatedAt(LocalDateTime.now());
			priority.setUpdatedBy(userName);
		}

		return priority;
	}

}
