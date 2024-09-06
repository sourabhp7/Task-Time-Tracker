package task.time.tracker.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

	@PreAuthorize("hasRole('SUPERADMIN')")
	@GetMapping()
	public ResponseEntity<?> getAllPermission() {
		LOGGER.info("getAllPermission>>start>>PermissionController");
		List<PermissionRequest> permissionRequests = permissionServiceImpl.findAllPermission();
		if (permissionRequests.isEmpty()) {
			ApiError apiError = new ApiError(HttpStatus.NOT_FOUND);
			apiError.setMessage("No Permission is Present");
			LOGGER.error("Permission>>Is>>NotPresent");
			return new ResponseEntity<>(apiError, HttpStatus.NOT_ACCEPTABLE);
		} else {
			SuccessResponse successResponse = new SuccessResponse();
			successResponse.setTimestamp(LocalDateTime.now());
			successResponse.setMessage("ALL PERMISSION");
			successResponse.setStatus(HttpStatus.OK);
			successResponse.setData(permissionRequests);
			LOGGER.info("getAllRoleFunction>>Done>>RoleController");
			return new ResponseEntity<>(successResponse, HttpStatus.OK);
		}

	}

	@PreAuthorize("hasRole('SUPERADMIN')")
	@GetMapping("/{permissionId}")
	public ResponseEntity<?> getById(@PathVariable(name = "permissionId", required = true) Long id) {
		LOGGER.info("getBYIdFunction>>start>>PermissionController");
		PermissionRequest permissionRequest = permissionServiceImpl.findById(id);
		SuccessResponse successResponse = new SuccessResponse();
		successResponse.setTimestamp(LocalDateTime.now());
		successResponse.setMessage("PERMISSION");
		successResponse.setStatus(HttpStatus.OK);
		successResponse.setData(permissionRequest);
		LOGGER.info("getBYIdFunction>>Done>>PermissionController");
		return new ResponseEntity<>(successResponse, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('SUPERADMIN')")
	@PostMapping()
	ResponseEntity<?> addRole(@Valid @RequestBody PermissionRequest permissionRequest) {
		LOGGER.info("Addrole>>start>>Controller");
		Optional<PermissionEntity> permissionEntity = pemissionEntityRepository
				.findByPermissonNameIgnoreCase(permissionRequest.getPermissionName());
		if (permissionEntity.isPresent()) {
			ApiError apiError = new ApiError(HttpStatus.NOT_ACCEPTABLE);
			apiError.setMessage("Permission Name is Already Present");
			LOGGER.error("Permission Name>>Already>>Present");
			return new ResponseEntity<>(apiError, HttpStatus.NOT_ACCEPTABLE);
		} else {
			permissionServiceImpl.save(permissionRequest, Operation.ADD.value());
			LOGGER.info("RoleName>>Created>>Controller");
			SuccessResponse successResponse = new SuccessResponse();
			successResponse.setTimestamp(LocalDateTime.now());
			successResponse.setMessage("Permission Created Successfully");
			successResponse.setStatus(HttpStatus.CREATED);
			return new ResponseEntity<>(successResponse, HttpStatus.CREATED);
		}
	}

	@PreAuthorize("hasRole('SUPERADMIN')")
	@PutMapping("{permissionId}")
	ResponseEntity<?> addRole(@PathVariable(name = "permissionId", required = true) Long id,
			@Valid @RequestBody PermissionRequest permissionRequest) {
		LOGGER.info("EditPermission>>start>>Controller");
		Optional<PermissionEntity> permissionEntity = pemissionEntityRepository
				.findByPermissonNameIgnoreCase(permissionRequest.getPermissionName());
		if (permissionEntity.isPresent()) {
			ApiError apiError = new ApiError(HttpStatus.NOT_ACCEPTABLE);
			apiError.setMessage("Permission Name is Already Present");
			LOGGER.error("Permission Name>>Already>>Present");
			return new ResponseEntity<>(apiError, HttpStatus.NOT_ACCEPTABLE);
		} else {
			permissionRequest.setId(id);
			permissionServiceImpl.save(permissionRequest, Operation.MODIFIED.value());
			LOGGER.info("Permission>>Edited>>Controller");
			SuccessResponse successResponse = new SuccessResponse();
			successResponse.setTimestamp(LocalDateTime.now());
			successResponse.setMessage("Permission Created Successfully");
			successResponse.setStatus(HttpStatus.CREATED);
			successResponse.setData(null);
			return new ResponseEntity<>(successResponse, HttpStatus.CREATED);
		}
	}
}
