package task.time.tracker.service;


import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import task.time.tracker.dto.PrivilageDTO;
import task.time.tracker.dto.RoleDTO;
import task.time.tracker.dto.UserDTO;
import task.time.tracker.model.RoleEntity;
import task.time.tracker.model.UserEntity;
import task.time.tracker.model.UserRoleEntity;
import task.time.tracker.repository.RolePermissionEntityRepository;
import task.time.tracker.repository.UserEntityRepository;
import task.time.tracker.repository.UserRoleEntityRepository;
import task.time.tracker.transformation.PrivilageTransformation;
import task.time.tracker.transformation.RoleTransformation;

@Service
public class UserEntityServiceImpl implements UserEntityService {

	@Autowired
	private UserEntityRepository userEntityRepository;

	@Autowired
	private UserRoleEntityRepository userRoleEntityRepository;

	@Autowired
	private RolePermissionEntityRepository rolePermissionEntityRepository;

	@Autowired
	private RoleTransformation roleTransformation;

	@Autowired
	private PrivilageTransformation privilageTransformation;

	@Override
	public UserDTO findByUserName(String userName) {

		Optional<UserEntity> userEntityOptional = userEntityRepository.findByUserName(userName.trim());
		UserDTO userDTO = null;
		if (userEntityOptional.isPresent()) {
			userDTO = new UserDTO();
			Set<RoleDTO> roleDtos = new HashSet<>();

			// Fetch the user roles
			UserEntity userEntity = userEntityOptional.get();
			userDTO.setUserName(userEntity.getUserName());
			userDTO.setPassword(userEntity.getPassword());
			Set<UserRoleEntity> userRoles = userRoleEntityRepository.findByPkUserEntity(userEntity);

			// Transform user roles to RoleDTOs
			userRoles.forEach(userRole -> {
				RoleEntity roleEntity = userRole.getPk().getRoleEntity();
				List<PrivilageDTO> privilageDTOs = rolePermissionEntityRepository.findByPkRole(roleEntity).stream()
						.map(rolePermissionEntity -> privilageTransformation
								.entityToObject(rolePermissionEntity.getPk().getPermission()))
						.collect(Collectors.toList());

				RoleDTO roleDTO = roleTransformation.entityToObject(roleEntity);
				roleDTO.setPrivilageDTOs(privilageDTOs);
				roleDtos.add(roleDTO);
			});

			userDTO.setRoleDTOs(roleDtos);
		}

		return userDTO;
	}

}
