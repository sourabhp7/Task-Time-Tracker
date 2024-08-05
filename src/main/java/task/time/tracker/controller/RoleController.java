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

	@PostMapping()
	ResponseEntity<?> addRole(@Valid @RequestBody RoleRequest roleRequest) {
		LOGGER.info("Addrole>>start>>Controller");
		Optional<RoleEntity> roleEntity = roleEntityRepository
				.findByRoleNameContainingIgnoreCase(roleRequest.getRoleName());
		if (roleEntity.isPresent()) {
			ApiError apiError = new ApiError(HttpStatus.NOT_ACCEPTABLE);
			apiError.setMessage("Role Name is Already Present");
			LOGGER.error("RoleName>>Already>>Present");
			return new ResponseEntity<>(apiError, HttpStatus.NOT_ACCEPTABLE);
		} else {
			roleService.addRole(roleRequest, Operation.ADD.value());
			LOGGER.info("RoleName>>Created>>Controller");
			SuccessResponse successResponse = new SuccessResponse();
			successResponse.setMessage("Role Created Successfully");
			successResponse.setStatus(HttpStatus.CREATED);
			return new ResponseEntity<>(successResponse, HttpStatus.CREATED);
		}
	}
}
