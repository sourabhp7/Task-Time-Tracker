package task.time.tracker.transformation;

import java.security.Permission;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import task.time.tracker.dto.PermissionRequest;
import task.time.tracker.dto.PrivilageDTO;
import task.time.tracker.dto.RoleDTO;
import task.time.tracker.dto.RoleRequest;
import task.time.tracker.exception.ResourceNotFound;
import task.time.tracker.model.PermissionEntity;
import task.time.tracker.model.RoleEntity;
import task.time.tracker.repository.PemissionEntityRepository;

@Component
public class PrivilageTransformation {

	@Autowired
	private PemissionEntityRepository pemissionEntityRepository;

	public PrivilageDTO entityToObject(PermissionEntity permissionEntity) {
		PrivilageDTO privilageDTO = new PrivilageDTO();
		privilageDTO.setId(permissionEntity.getId());
		privilageDTO.setPrivilageName(permissionEntity.getPermissonName());
		return privilageDTO;
	}

	public PermissionEntity DtoToEntity(PermissionRequest permissionRequest, String Operation) {
		PermissionEntity permissionEntity = null;
		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		final String userName = auth.getName();
		if (task.time.tracker.constant.Operation.MODIFIED.value().equalsIgnoreCase(Operation)) {
			permissionEntity = pemissionEntityRepository.findById(permissionRequest.getId())
					.orElseThrow(() -> new ResourceNotFound("Permission Id Not Found"));

		}
		if (permissionEntity == null) {
			permissionEntity = new PermissionEntity();
		}
		permissionEntity.setPermissonName(permissionRequest.getPermissionName());
		if (task.time.tracker.constant.Operation.ADD.value().equalsIgnoreCase(Operation)) {
			permissionEntity.setCreatedAt(LocalDateTime.now());
			permissionEntity.setUpdatedAt(LocalDateTime.now());
			permissionEntity.setCreatedBy(userName);
			permissionEntity.setUpdatedBy(userName);
		} else if (task.time.tracker.constant.Operation.MODIFIED.value().equalsIgnoreCase(Operation)) {
			permissionEntity.setUpdatedAt(LocalDateTime.now());
			permissionEntity.setUpdatedBy(userName);
		}

		return permissionEntity;
	}

	public PermissionRequest entityToPermissionRequest(PermissionEntity permissionEntity) {
		PermissionRequest permissionRequest = new PermissionRequest();
		permissionRequest.setId(permissionEntity.getId());
		permissionRequest.setPermissionName(permissionEntity.getPermissonName());
		return permissionRequest;
	}
}
