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
import task.time.tracker.dto.RoleRequest;
import task.time.tracker.dto.SuccessResponse;
import task.time.tracker.exception.ApiError;
import task.time.tracker.model.RoleEntity;
import task.time.tracker.repository.RoleEntityRepository;
import task.time.tracker.service.RoleService;

@RestController
@RequestMapping("/role")
public class RoleController {
	public static final Logger LOGGER = LoggerFactory.getLogger(RoleController.class);
	@Autowired
	private RoleService roleService;

	@Autowired
	private RoleEntityRepository roleEntityRepository;

	@PreAuthorize("hasRole('SUPERADMIN')")
	@GetMapping()
	public ResponseEntity<?> getAllRole() {
		LOGGER.info("getAllRole>>start>>RoleController");
		List<RoleRequest> roleRequests = roleService.findAllRole();
		if (roleRequests.isEmpty()) {
			ApiError apiError = new ApiError(HttpStatus.NOT_FOUND);
			apiError.setMessage("No Role is Present");
			LOGGER.error("Permission>>Is>>NotPresent");
			return new ResponseEntity<>(apiError, HttpStatus.NOT_ACCEPTABLE);
		} else {
			SuccessResponse successResponse = new SuccessResponse();
			successResponse.setTimestamp(LocalDateTime.now());
			successResponse.setMessage("ALL ROLE");
			successResponse.setStatus(HttpStatus.OK);
			successResponse.setData(roleRequests);
			LOGGER.info("getAllRoleFunction>>Done>>RoleController");
			return new ResponseEntity<>(successResponse, HttpStatus.OK);
		}
	}
	@PreAuthorize("hasRole('SUPERADMIN')")
	@GetMapping("/{roleId}")
	public ResponseEntity<?> getById(@PathVariable(name = "roleId", required = true) Long id) {
		LOGGER.info("getBYIdFunction>>start>>RoleController");
		RoleRequest roleRequest = roleService.findById(id);
		SuccessResponse successResponse = new SuccessResponse();
		successResponse.setTimestamp(LocalDateTime.now());
		successResponse.setMessage("Role");
		successResponse.setStatus(HttpStatus.OK);
		successResponse.setData(roleRequest);
		return new ResponseEntity<>(successResponse, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('SUPERADMIN')")
	@PostMapping()
	public ResponseEntity<?> addRole(@Valid @RequestBody RoleRequest roleRequest) {
		LOGGER.info("Addrole>>start>>Controller");
		RoleEntity roleEntity = roleEntityRepository.findByRoleNameIgnoreCase(roleRequest.getRoleName()).orElse(null);
		if (roleEntity != null) {
			ApiError apiError = new ApiError(HttpStatus.NOT_ACCEPTABLE);
			apiError.setMessage("Role Name is Already Present");
			LOGGER.error("RoleName>>Already>>Present");
			return new ResponseEntity<>(apiError, HttpStatus.NOT_ACCEPTABLE);
		} else {
			roleService.addRole(roleRequest, Operation.ADD.value());
			LOGGER.info("RoleName>>Created>>Controller");
			SuccessResponse successResponse = new SuccessResponse();
			successResponse.setTimestamp(LocalDateTime.now());
			successResponse.setMessage("Role Created Successfully");
			successResponse.setStatus(HttpStatus.CREATED);
			successResponse.setData(null);
			return new ResponseEntity<>(successResponse, HttpStatus.CREATED);
		}
	}

	@PreAuthorize("hasRole('SUPERADMIN')")
	@PutMapping("{roleId}")
	public ResponseEntity<?> editRole(@PathVariable(name = "roleId", required = true) Long id,
			@Valid @RequestBody RoleRequest roleRequest) {
		LOGGER.info("editRole>>start>>Controller");
		RoleEntity roleEntity = roleEntityRepository.findByRoleNameIgnoreCase(roleRequest.getRoleName()).orElse(null);
		if (roleEntity != null) {
			ApiError apiError = new ApiError(HttpStatus.NOT_ACCEPTABLE);
			apiError.setMessage("Role Name is Already Present");
			LOGGER.error("RoleName>>Already>>Present");
			return new ResponseEntity<>(apiError, HttpStatus.NOT_ACCEPTABLE);
		} else {
			roleRequest.setId(id);
			roleService.addRole(roleRequest, Operation.MODIFIED.value());
			LOGGER.info("RoleName>>EDIT>>Controller");
			SuccessResponse successResponse = new SuccessResponse();
			successResponse.setTimestamp(LocalDateTime.now());
			successResponse.setMessage("Role Edited Successfully");
			successResponse.setStatus(HttpStatus.OK);
			successResponse.setData(null);
			return new ResponseEntity<>(successResponse, HttpStatus.OK);
		}
	}

}
