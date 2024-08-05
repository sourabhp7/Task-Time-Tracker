package task.time.tracker.service;

import task.time.tracker.dto.PermissionRequest;

public interface PermissionService {

	public void save(final PermissionRequest permissionRequest,final String Operation);
}
