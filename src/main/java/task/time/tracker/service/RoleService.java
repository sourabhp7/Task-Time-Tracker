package task.time.tracker.service;

import java.util.List;

import task.time.tracker.dto.RoleRequest;

public interface RoleService {

	public void addRole(RoleRequest roleRequest, String Operation);
	
	public RoleRequest findById(final Long id);
	
	public List<RoleRequest> findAllRole();
}
