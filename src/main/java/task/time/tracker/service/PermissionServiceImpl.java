package task.time.tracker.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import task.time.tracker.dto.PermissionRequest;
import task.time.tracker.exception.ResourceNotFound;
import task.time.tracker.model.PermissionEntity;
import task.time.tracker.repository.PemissionEntityRepository;
import task.time.tracker.transformation.PrivilageTransformation;

@Service
public class PermissionServiceImpl implements PermissionService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PermissionServiceImpl.class);
	@Autowired
	private PemissionEntityRepository pemissionEntityRepository;

	@Autowired
	private PrivilageTransformation privilageTransformation;

	@Override
	public void save(PermissionRequest permissionRequest, String Operation) {
		LOGGER.info("savePermission() >> Create >> Start");
		PermissionEntity permissionEntity = privilageTransformation.DtoToEntity(permissionRequest, Operation);
		pemissionEntityRepository.save(permissionEntity);
		LOGGER.info("SavePermission() >> Saved >> Done");
	}

	@Override
	public PermissionRequest findById(Long id) {
		LOGGER.info("findById() >> Get >> Start");
		PermissionEntity permissionEntity = pemissionEntityRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFound("Permission Id Not found"));
		PermissionRequest permissionRequest = privilageTransformation.entityToPermissionRequest(permissionEntity);
		LOGGER.info("findById() >> Get >> Done");
		return permissionRequest;
	}

	@Override
	public List<PermissionRequest> findAllPermission() {
		LOGGER.info("findALLROLE() >> Get >> Start");
		List<PermissionEntity> permissionEntities = pemissionEntityRepository.findAll();
		List<PermissionRequest> permissionRequests = new ArrayList<>();
		if (!permissionEntities.isEmpty()) {
			permissionEntities.stream()
					.forEach(p -> permissionRequests.add(privilageTransformation.entityToPermissionRequest(p)));
		}
		LOGGER.info("findALLROLE() >> Get >> Done");
		return permissionRequests;
	}

}
