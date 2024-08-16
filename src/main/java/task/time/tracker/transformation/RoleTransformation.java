package task.time.tracker.transformation;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import task.time.tracker.dto.RoleDTO;
import task.time.tracker.dto.RoleRequest;
import task.time.tracker.exception.ResourceNotFound;
import task.time.tracker.model.RoleEntity;
import task.time.tracker.repository.RoleEntityRepository;

@Component
public class RoleTransformation {

	@Autowired
	RoleEntityRepository roleEntityRepository;

	public RoleDTO entityToObject(RoleEntity roleEntity) {
		RoleDTO roleDTO = new RoleDTO();
		roleDTO.setId(roleEntity.getId());
		roleDTO.setRoleName(roleEntity.getRoleName());
		return roleDTO;
	}

	public RoleEntity DtoToEntity(RoleRequest roleRequest, String Operation) {
		RoleEntity roleEntity = null;
		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		final String userName = auth.getName();
		if (task.time.tracker.constant.Operation.MODIFIED.value().equalsIgnoreCase(Operation)) {
			roleEntity = roleEntityRepository.findById(roleRequest.getId()).orElseThrow(()-> new ResourceNotFound("Role Id Not Found "));

		}
		if (roleEntity == null) {
			roleEntity = new RoleEntity();
		}
		roleEntity.setRoleName(roleRequest.getRoleName());
		if (task.time.tracker.constant.Operation.ADD.value().equalsIgnoreCase(Operation)) {
			roleEntity.setCreatedAt(LocalDateTime.now());
			roleEntity.setUpdatedAt(LocalDateTime.now());
			roleEntity.setCreatedBy(userName);
			roleEntity.setUpdatedBy(userName);
		} else if (task.time.tracker.constant.Operation.MODIFIED.value().equalsIgnoreCase(Operation)) {
			roleEntity.setUpdatedAt(LocalDateTime.now());
			roleEntity.setUpdatedBy(userName);
		}

		return roleEntity;
	}
	
	public RoleRequest entityToRoleRequest(RoleEntity roleEntity) {
		RoleRequest roleDTO = new RoleRequest();
		roleDTO.setId(roleEntity.getId());
		roleDTO.setRoleName(roleEntity.getRoleName());
		return roleDTO;
	}
}
