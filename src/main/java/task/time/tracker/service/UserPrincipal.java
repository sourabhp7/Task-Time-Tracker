package task.time.tracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import task.time.tracker.dto.UserDTO;

@Service
public class UserPrincipal implements UserDetailsService {

	@Autowired
	private UserEntityService userEntityService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDTO userDTO = userEntityService.findByUserName(username);
		if (userDTO == null) {
			throw new UsernameNotFoundException("User not Found");
		}
		return new UserPrincipalService(userDTO);
	}

}
