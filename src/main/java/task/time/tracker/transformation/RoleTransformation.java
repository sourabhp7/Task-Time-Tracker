package task.time.tracker.transformation;

import org.springframework.stereotype.Component;

import task.time.tracker.dto.RoleDTO;
import task.time.tracker.model.RoleEntity;

@Component
public class RoleTransformation {

	public RoleDTO entityToObject(RoleEntity roleEntity) {
		RoleDTO roleDTO = new RoleDTO();
		roleDTO.setId(roleEntity.getId());
		roleDTO.setRoleName(roleEntity.getRoleName());
		return roleDTO;
	}
}
