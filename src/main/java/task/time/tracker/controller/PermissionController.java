package task.time.tracker.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import task.time.tracker.constant.Operation;
import task.time.tracker.dto.PermissionRequest;
import task.time.tracker.dto.SuccessResponse;
import task.time.tracker.exception.ApiError;
import task.time.tracker.model.PermissionEntity;
import task.time.tracker.repository.PemissionEntityRepository;
import task.time.tracker.service.PermissionServiceImpl;

@RestController
@RequestMapping("/permission")
public class PermissionController {
	public static final Logger LOGGER = LoggerFactory.getLogger(PermissionController.class);
	@Autowired
	private PemissionEntityRepository pemissionEntityRepository;
	@Autowired
	private PermissionServiceImpl permissionServiceImpl;
	
	@PostMapping()
	ResponseEntity<?> addRole(@Valid @RequestBody PermissionRequest permissionRequest) {
		LOGGER.info("Addrole>>start>>Controller");
		Optional<PermissionEntity> permissionEntity = pemissionEntityRepository
				.findByPermissonNameContainingIgnoreCase(permissionRequest.getPermissionName());
		if (permissionEntity.isPresent()) {
			ApiError apiError = new ApiError(HttpStatus.NOT_ACCEPTABLE);
			apiError.setMessage("Permission Name is Already Present");
			LOGGER.error("Permission Name>>Already>>Present");
			return new ResponseEntity<>(apiError, HttpStatus.NOT_ACCEPTABLE);
		} else {
			permissionServiceImpl.save(permissionRequest, Operation.ADD.value());
			LOGGER.info("RoleName>>Created>>Controller");
			SuccessResponse successResponse = new SuccessResponse();
			successResponse.setMessage("Role Created Successfully");
			successResponse.setStatus(HttpStatus.CREATED);
			return new ResponseEntity<>(successResponse, HttpStatus.CREATED);
		}
	}
}
