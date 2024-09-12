package task.time.tracker.transformation;

import java.time.LocalDateTime;

import org.apache.catalina.authenticator.SpnegoAuthenticator.AuthenticateAction;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import task.time.tracker.constant.Operation;
import task.time.tracker.dto.EmployeeDTO;
import task.time.tracker.model.Employee;
import task.time.tracker.repository.EmployeeRepository;

@Component
public class EmployeeTransformation {
	
	private EmployeeRepository employeeRepository;

	public EmployeeDTO entityToObject(Employee employee) {
		EmployeeDTO employeeDTO = new EmployeeDTO();
		employeeDTO.setId(employee.getId());
		employeeDTO.setName(employee.getName());
		employeeDTO.setEmailId(employee.getEmailId());
		employeeDTO.setBloodGroup(employee.getBloodGroup());
		employeeDTO.setAddress(employee.getAddress());
		employeeDTO.setCity(employee.getCity());
		employeeDTO.setDateOfBirth(employee.getDateOfBirth());
		employeeDTO.setMobileNo(employee.getMobileNo());
		employeeDTO.setResgistrationNo(employee.getResgistrationNo());
		employeeDTO.setImageUrl(employee.getImageUrl());

		return employeeDTO;

	}

	public Employee DtoToEntity(EmployeeDTO employeeDTO, String operation) {
		Employee employee=null;
		
		final Authentication auth=SecurityContextHolder.getContext().getAuthentication();
		final String userName=auth.getName();
		
		if (Operation.MODIFIED.value().equalsIgnoreCase(operation)) {
			employee=employeeRepository.findById(employeeDTO.getId()).get();
		}
		
		if(employee==null) {
			employee=new Employee();
		}
		
		employee.setName(employeeDTO.getName());
		employee.setEmailId(employeeDTO.getEmailId());
		employee.setAddress(employeeDTO.getAddress());
		employee.setBloodGroup(employeeDTO.getBloodGroup());
		employee.setDateOfBirth(employeeDTO.getDateOfBirth());
		employee.setCity(employeeDTO.getCity());
		employee.setResgistrationNo(employeeDTO.getResgistrationNo());
		employee.setImageUrl(employeeDTO.getImageUrl());
		employee.setIsActive(employeeDTO.getIsActive());
		
		if(Operation.ADD.value().equalsIgnoreCase(operation)) {
			employee.setCreatedAt(LocalDateTime.now());
			employee.setCreatedBy(userName);
			employee.setUpdatedAt(LocalDateTime.now());			
			employee.setUpdatedBy(userName);
		}
		
		if(Operation.MODIFIED.value().equalsIgnoreCase(operation)) {
			employee.setUpdatedAt(LocalDateTime.now());
			employee.setUpdatedBy(userName);
		}
		
		return employee;
	}

}
