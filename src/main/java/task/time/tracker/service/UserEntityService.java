package task.time.tracker.service;

import task.time.tracker.dto.UserDTO;

public interface UserEntityService {

	public UserDTO findByUserName(String userName);
}
