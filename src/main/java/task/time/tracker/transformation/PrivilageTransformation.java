package task.time.tracker.transformation;

import java.security.Permission;

import org.springframework.stereotype.Component;

import task.time.tracker.dto.PrivilageDTO;
import task.time.tracker.dto.RoleDTO;
import task.time.tracker.model.PermissionEntity;
import task.time.tracker.model.RoleEntity;

@Component
public class PrivilageTransformation {
	public PrivilageDTO entityToObject(PermissionEntity permissionEntity) {
		PrivilageDTO privilageDTO = new PrivilageDTO();
		privilageDTO.setId(permissionEntity.getId());
		privilageDTO.setPrivilageName(permissionEntity.getPermissonName());
		return privilageDTO;
	}
}
