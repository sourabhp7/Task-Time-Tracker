package task.time.tracker.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import task.time.tracker.dto.RoleRequest;
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

}
