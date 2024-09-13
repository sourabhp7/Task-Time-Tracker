package task.time.tracker.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import task.time.tracker.constant.Operation;
import task.time.tracker.dto.EmployeeDTO;
import task.time.tracker.dto.SuccessResponse;
import task.time.tracker.exception.ApiError;
import task.time.tracker.exception.DataAlreadyExistException;
import task.time.tracker.exception.ResourceNotFound;
import task.time.tracker.model.Employee;
import task.time.tracker.repository.EmployeeRepository;
import task.time.tracker.service.EmployeeService;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private EmployeeRepository employeeRepository;

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
			successResponse.setData(employeeDTOs);
			return new ResponseEntity<>(successResponse, HttpStatus.OK);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getEmployeeById(@PathVariable Long id) {
		try {
			EmployeeDTO employeeDTO = employeeService.getByEmployeeId(id);
			SuccessResponse successResponse = new SuccessResponse();
			successResponse.setTimestamp(LocalDateTime.now());
			successResponse.setData(employeeDTO);
			return new ResponseEntity<>(successResponse, HttpStatus.OK);
		} catch (Exception e) {
			ApiError apiError = new ApiError(HttpStatus.NOT_FOUND);
			apiError.setMessage("employee id: " + id + " not found");
			return new ResponseEntity<>(apiError, HttpStatus.NOT_ACCEPTABLE);
		}

	}

	@PostMapping()
	public ResponseEntity<?> addEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) {
		Employee registerdEmployee = employeeRepository
				.findByResgistrationNoIgnoreCase(employeeDTO.getResgistrationNo()).orElse(null);
		if (registerdEmployee != null) {
			ApiError apiError = new ApiError(HttpStatus.NOT_ACCEPTABLE);
			apiError.setMessage("employee is already  present");
			return new ResponseEntity<>(apiError, HttpStatus.NOT_ACCEPTABLE);
		} else {
			try {
				employeeService.save(employeeDTO, Operation.ADD.value());
				SuccessResponse successResponse = new SuccessResponse();
				successResponse.setTimestamp(LocalDateTime.now());
				successResponse.setMessage("employee created succesfully");
				return new ResponseEntity<>(successResponse, HttpStatus.CREATED);
			} catch (Exception e) {
				ApiError apiError = new ApiError(HttpStatus.NOT_FOUND);
				apiError.setMessage(e.getMessage());
				throw new ResourceNotFound(apiError);

			}

		}

	}

	@PutMapping("/{id}")
	public ResponseEntity<?> editEmployee(@PathVariable Long id,@Valid @RequestBody EmployeeDTO employeeDTO) {

		boolean isRegistrationNoExitsWhileUpdate = employeeService
				.isRegistrationNoExitsWhileUpdate(employeeDTO.getResgistrationNo(), id);
		if (isRegistrationNoExitsWhileUpdate == true) {
			final ApiError apiError = new ApiError(HttpStatus.CONFLICT);
			apiError.setMessage("Error: you updating employee registered number!");
			throw new DataAlreadyExistException(apiError);
		}
		try {
			employeeDTO.setId(id);
			employeeService.save(employeeDTO, Operation.MODIFIED.value());
			SuccessResponse successResponse = new SuccessResponse();
			successResponse.setStatus(HttpStatus.OK);
			successResponse.setData(employeeDTO);
			successResponse.setTimestamp(LocalDateTime.now());
			successResponse.setMessage("update successfully");
			return new ResponseEntity<>(successResponse, HttpStatus.OK);
		} catch (Exception e) {
			ApiError apiError = new ApiError(HttpStatus.NOT_FOUND);
			apiError.setMessage(e.getMessage());
			throw new ResourceNotFound(apiError);
		}

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteByid(@PathVariable Long id) {
		try {
			employeeService.deleteById(id);
			SuccessResponse successResponse = new SuccessResponse();
			successResponse.setMessage("employee id " + id + " is deleted");
			successResponse.setStatus(HttpStatus.OK);
			successResponse.setTimestamp(LocalDateTime.now());
			return new ResponseEntity<>(successResponse, HttpStatus.ACCEPTED);

		} catch (Exception e) {
			ApiError apiError = new ApiError(HttpStatus.NOT_FOUND);
			apiError.setMessage("employee id: " + id + " not found");
			return new ResponseEntity<>(apiError, HttpStatus.NOT_ACCEPTABLE);
		}

	}

}
