package task.time.tracker.transformation;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import task.time.tracker.constant.Operation;
import task.time.tracker.dto.PrivilageDTO;
import task.time.tracker.dto.TaskDTO;
import task.time.tracker.model.PermissionEntity;
import task.time.tracker.model.Priority;
import task.time.tracker.model.TaskEntity;
import task.time.tracker.repository.PriorityRepository;
import task.time.tracker.repository.TaskRepository;
import task.time.tracker.utils.DateUtils;

@Component
public class TaskTransformation {

	@Autowired
	private TaskRepository taskRepository;

	@Autowired
	private DateUtils dateUtils;

	@Autowired
	private PriorityRepository priorityRepository;

	public TaskDTO entityToObject(TaskEntity taskEntity) {
		TaskDTO taskDTO = new TaskDTO();
		taskDTO.setId(taskEntity.getId());
		taskDTO.setTaskName(taskEntity.getTaskName());
		taskDTO.setDeadLineDate(
				dateUtils.convertLocalDateTimeToString(taskEntity.getDeadLineDate(), "dd/MM/yyyy HH:mm:ss"));
		taskDTO.setEstimatedTime(
				dateUtils.convertLocalDateTimeToString(taskEntity.getEstimatedTime(), "dd/MM/yyyy HH:mm:ss"));
		taskDTO.setStatus(taskEntity.getStatus());
		taskDTO.setNotes(taskEntity.getNotes());
		taskDTO.setPriorityId(taskEntity.getPriority().getId());
		return taskDTO;
	}

	public TaskEntity ObjectToEntity(TaskDTO taskDto, String operation) {
		TaskEntity taskEntity = null;
		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		final String userName = auth.getName();
		if (operation.equals(Operation.MODIFIED.value())) {
			taskEntity = taskRepository.findById(taskDto.getId()).get();

		}
		if (taskEntity == null) {
			taskEntity = new TaskEntity();
		}
		taskEntity.setId(taskDto.getId());
		taskEntity.setTaskName(taskDto.getTaskName());
		taskEntity.setStatus(taskDto.getStatus());
		taskEntity.setNotes(taskDto.getNotes());
		taskEntity.setDeadLineDate(
				dateUtils.convertStringToLocalDateTime(taskDto.getDeadLineDate(), "dd/MM/yyyy HH:mm:ss"));
		taskEntity.setEstimatedTime(
				dateUtils.convertStringToLocalDateTime(taskDto.getEstimatedTime(), "dd/MM/yyyy HH:mm:ss"));

		Priority priority = priorityRepository.findById(taskDto.getPriorityId()).get();
		taskEntity.setPriority(priority);
		if (Operation.ADD.value().equalsIgnoreCase(operation)) {
			taskEntity.setCreatedAt(LocalDateTime.now());
			taskEntity.setUpdatedAt(LocalDateTime.now());
			taskEntity.setCreatedBy(userName);
			taskEntity.setUpdatedBy(userName);
		} else if (Operation.MODIFIED.value().equalsIgnoreCase(operation)) {
			taskEntity.setUpdatedAt(LocalDateTime.now());
			taskEntity.setUpdatedBy(userName);
		}

		return taskEntity;

	}

}
