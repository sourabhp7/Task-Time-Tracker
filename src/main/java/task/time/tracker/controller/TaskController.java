package task.time.tracker.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import task.time.tracker.dto.TaskDTO;

@RestController
@RequestMapping("/task")
public class TaskController {

@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
public ResponseEntity<?> addTask(@ModelAttribute TaskDTO taskDTO)
{
	System.out.println(taskDTO);
	return null;
}
}
