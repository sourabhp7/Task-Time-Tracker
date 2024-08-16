package task.time.tracker.service;

import java.util.ArrayList;
import java.util.List;

import javax.management.relation.Role;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import task.time.tracker.dto.RoleRequest;
import task.time.tracker.exception.ResourceNotFound;
import task.time.tracker.model.RoleEntity;
import task.time.tracker.repository.RoleEntityRepository;
import task.time.tracker.transformation.RoleTransformation;

@Service
public class RoleServiceImpl implements RoleService {

	private static final Logger LOGGER = LoggerFactory.getLogger(RoleServiceImpl.class);

	@Autowired
	private RoleEntityRepository roleEntityRepository;

	@Autowired
	private RoleTransformation roleTransformation;

	@Override
	public void addRole(RoleRequest roleRequest, String Operation) {
		LOGGER.info("addRole() >> Create >> Start");
		RoleEntity roleEntity = roleTransformation.DtoToEntity(roleRequest, Operation);
		roleEntityRepository.save(roleEntity);
		LOGGER.info("addRole() >> Saved >> Done");
	}

	@Override
	public RoleRequest findById(Long id) {
		LOGGER.info("findById() >> Get >> Start");
		RoleEntity roleEntity = roleEntityRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFound("Role Id Not found"));
		RoleRequest roleRequest = roleTransformation.entityToRoleRequest(roleEntity);
		LOGGER.info("findById() >> Get >> Done");
		return roleRequest;
	}

	@Override
	public List<RoleRequest> findAllRole() {
		LOGGER.info("findALLROLE() >> Get >> Start");
		List<RoleEntity> roleEntities = roleEntityRepository.findAll();
		List<RoleRequest> roleRequests = new ArrayList<>();
		if (!roleEntities.isEmpty()) {
			roleEntities.stream().forEach(role -> roleRequests.add(roleTransformation.entityToRoleRequest(role)));
		}
		LOGGER.info("findALLROLE() >> Get >> Done");
		return roleRequests;
	}

}
