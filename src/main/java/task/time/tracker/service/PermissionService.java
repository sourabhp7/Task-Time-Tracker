package task.time.tracker.service;

import java.util.List;

import task.time.tracker.dto.PermissionRequest;

public interface PermissionService {

	public void save(final PermissionRequest permissionRequest,final String Operation);
	
	public PermissionRequest findById(final Long id);
	
	public List<PermissionRequest> findAllPermission();
}
