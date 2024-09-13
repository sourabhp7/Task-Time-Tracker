package task.time.tracker.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import task.time.tracker.dto.EmployeeDTO;
import task.time.tracker.exception.ResourceNotFound;
import task.time.tracker.model.Employee;
import task.time.tracker.repository.EmployeeRepository;
import task.time.tracker.transformation.EmployeeTransformation;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private EmployeeTransformation employeeTransformation;

	@Override
	public List<EmployeeDTO> getAllEmployee() {
		List<Employee> employees = employeeRepository.findAll();
		List<EmployeeDTO> employeeDTOs = new ArrayList<>();
		for (Employee employee : employees) {
			employeeDTOs.add(employeeTransformation.entityToObject(employee));
		}
		return employeeDTOs;
	}

	@Override
	public EmployeeDTO getByEmployeeId(Long id) {
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFound("employee id not found"));

		EmployeeDTO employeeDTO = employeeTransformation.entityToObject(employee);

		return employeeDTO;
	}

	@Override
	public EmployeeDTO save(EmployeeDTO employeeDTO, String Operation) {
		Employee employee = employeeTransformation.DtoToEntity(employeeDTO, Operation);
		final Employee employee2 = employeeRepository.save(employee);
		return employeeTransformation.entityToObject(employee2);
	}

	@Override
	public Employee updateEmployee(EmployeeDTO employeeDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteById(Long id) {
		Employee employee = employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFound("idnotfound"));
		employeeRepository.deleteById(employee.getId());

	}

	@Override
	public boolean isRegistrationNoExitsWhileUpdate(String resgistrationNo, Long id) {
		int count= employeeRepository.isRegistrationNoExitsWhileUpdate(resgistrationNo,id);
		if(count > 0) {
			return true;
		}
		return false;
	}

}
