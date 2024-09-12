package task.time.tracker.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import task.time.tracker.dto.EmployeeDTO;
import task.time.tracker.dto.SuccessResponse;
import task.time.tracker.exception.ApiError;
import task.time.tracker.service.EmployeeService;

@RestController
@RequestMapping("employee")
public class EmployeeController {

	private EmployeeService employeeService;

	@GetMapping()
	public ResponseEntity<?> getAllEmployee() {
		List<EmployeeDTO> employeeDTOs = employeeService.getAllEmployee();
		if (employeeDTOs.isEmpty()) {
			ApiError apiError = new ApiError(HttpStatus.NOT_FOUND);
			apiError.setMessage("Employess are empty");
			return new ResponseEntity<>(apiError, HttpStatus.NOT_ACCEPTABLE);
		} else {
			SuccessResponse successResponse = new SuccessResponse();
			successResponse.setTimestamp(LocalDateTime.now());
			return new ResponseEntity<>(successResponse, HttpStatus.OK);
		}
	}

}
