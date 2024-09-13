package task.time.tracker.service;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import task.time.tracker.dto.TaskDTO;
import task.time.tracker.model.TaskEntity;
import task.time.tracker.repository.TaskRepository;
import task.time.tracker.transformation.TaskTransformation;

@Service
public class TaskServiceImpl implements TaskService {

	private static final Logger LOGGER = LoggerFactory.getLogger(TaskServiceImpl.class);

	@Autowired
	private TaskTransformation taskTransformation;

	@Autowired
	private TaskRepository taskRepository;

	@Autowired
	private TaskFileService taskFileService;

	@Override
	public void save(TaskDTO taskDTO, String operation) {
		LOGGER.info("saveTask() >> Create >> Start");
		TaskEntity taskEntity = taskTransformation.ObjectToEntity(taskDTO, operation);
		TaskEntity saveTaskEntity = taskRepository.save(taskEntity);
		LOGGER.info("saveTask() >> Task >>Save");
		if (!CollectionUtils.isEmpty(taskDTO.getFiles())) {
			try {
				taskFileService.upoldTaskFile(taskDTO.getFiles(), saveTaskEntity);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				LOGGER.error(e.getMessage());
			}
		}
		LOGGER.info("saveTask() >> Task >>Completed");
	}

}
