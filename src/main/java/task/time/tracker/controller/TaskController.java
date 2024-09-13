package task.time.tracker.controller;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import task.time.tracker.constant.Operation;
import task.time.tracker.dto.SuccessResponse;
import task.time.tracker.dto.TaskDTO;
import task.time.tracker.service.TaskService;

@RestController
@RequestMapping("/task")
public class TaskController {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(TaskController.class);
	@Autowired
	private TaskService taskService;

	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> addTask(@ModelAttribute @Valid TaskDTO taskDTO) {
		LOGGER.info("AddTask>>start>>TaskController");
		taskService.save(taskDTO, Operation.ADD.value());
		LOGGER.info("AddTask>>Created>>TaskController");
		SuccessResponse successResponse = new SuccessResponse();
		successResponse.setTimestamp(LocalDateTime.now());
		successResponse.setMessage("Task Created Successfully");
		successResponse.setStatus(HttpStatus.CREATED);
		return new ResponseEntity<>(successResponse, HttpStatus.CREATED);
	}
}
