package task.time.tracker.service;

import java.util.List;

import task.time.tracker.dto.EmployeeDTO;
import task.time.tracker.model.Employee;

public interface EmployeeService {
	
	public List<EmployeeDTO>getAllEmployee();
	
	public EmployeeDTO getByEmployeeId(Long id);
	
	public EmployeeDTO save(EmployeeDTO employeeDTO,String Operation);
	
	public Employee updateEmployee(EmployeeDTO employeeDTO);
	
	public void deleteById(Long id);

	public boolean isRegistrationNoExitsWhileUpdate(String resgistrationNo, Long id);

}
