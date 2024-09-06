package task.time.tracker.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import task.time.tracker.constant.Operation;
import task.time.tracker.dto.PriorityDTO;
import task.time.tracker.dto.SuccessResponse;
import task.time.tracker.exception.ApiError;
import task.time.tracker.model.Priority;
import task.time.tracker.repository.PriorityRepository;
import task.time.tracker.service.PriorityService;

@RestController
@RequestMapping("/priority")
public class PriorityController {

	public static final Logger LOGGER = LoggerFactory.getLogger(PriorityController.class);
	@Autowired
	private PriorityService priorityService;

	@Autowired
	private PriorityRepository priorityRepository;

	@PreAuthorize("hasRole('SUPERADMIN')")
	@GetMapping()
	public ResponseEntity<?> getAllPriority() {
		LOGGER.info("getAllPrioritys>>start>>PriorityController");
		List<PriorityDTO> priorityDTOs = priorityService.findAll();
		if (priorityDTOs.isEmpty()) {
			ApiError apiError = new ApiError(HttpStatus.NOT_FOUND);
			apiError.setMessage("No Priority is Present");
			LOGGER.error("Priority>>Is>>NotPresent");
			return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
		} else {
			SuccessResponse successResponse = new SuccessResponse();
			successResponse.setTimestamp(LocalDateTime.now());
			successResponse.setMessage("ALL Priority");
			successResponse.setStatus(HttpStatus.OK);
			successResponse.setData(priorityDTOs);
			LOGGER.info("getAllRoleFunction>>Done>>PriorityController");
			return new ResponseEntity<>(successResponse, HttpStatus.OK);
		}
	}

	@PostMapping()
	public ResponseEntity<?> addPriority(@Valid @RequestBody PriorityDTO priorityDTO) {
		LOGGER.info("addPriority>>start>>PriorityController");
		Priority priority = priorityRepository.findByNameIgnoreCase(priorityDTO.getName()).orElse(null);
		if (priority != null) {
			ApiError apiError = new ApiError(HttpStatus.NOT_ACCEPTABLE);
			apiError.setMessage("Priority Name is Already Present");
			LOGGER.error("Priority>>Already>>Present");
			return new ResponseEntity<>(apiError, HttpStatus.NOT_ACCEPTABLE);
		} else {
			priorityService.save(priorityDTO, Operation.ADD.value());
			LOGGER.info("RoleName>>Created>>Controller");
			SuccessResponse successResponse = new SuccessResponse();
			successResponse.setTimestamp(LocalDateTime.now());
			successResponse.setMessage("Priority Created Successfully");
			successResponse.setStatus(HttpStatus.CREATED);
			successResponse.setData(null);
			return new ResponseEntity<>(successResponse, HttpStatus.CREATED);
		}
	}

	@PreAuthorize("hasRole('SUPERADMIN')")
	@PutMapping("{priorityId}")
	public ResponseEntity<?> editPriority(@PathVariable(name = "priorityId", required = true) Long id,
			@Valid @RequestBody PriorityDTO priorityDTO) {
		LOGGER.info("editPriority>>start>>PriorityController");
		Priority priority = priorityRepository.findByNameIgnoreCase(priorityDTO.getName()).orElse(null);
		if (priority != null) {
			ApiError apiError = new ApiError(HttpStatus.NOT_ACCEPTABLE);
			apiError.setMessage("Priority Name is Already Present");
			LOGGER.error("Priority>>Already>>Present");
			return new ResponseEntity<>(apiError, HttpStatus.NOT_ACCEPTABLE);
		} else {
			priorityDTO.setId(id);
			priorityService.save(priorityDTO, Operation.MODIFIED.value());
			LOGGER.info("PriorityNamr>>EDIT>>PriorityController");
			SuccessResponse successResponse = new SuccessResponse();
			successResponse.setTimestamp(LocalDateTime.now());
			successResponse.setMessage("Priority Edited Successfully");
			successResponse.setStatus(HttpStatus.OK);
			successResponse.setData(null);
			return new ResponseEntity<>(successResponse, HttpStatus.OK);
		}
	}
}
