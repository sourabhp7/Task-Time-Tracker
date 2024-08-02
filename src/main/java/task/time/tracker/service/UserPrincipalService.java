package task.time.tracker.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import task.time.tracker.dto.UserDTO;

public class UserPrincipalService implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UserDTO userDTO;

	public UserPrincipalService(UserDTO userDTO) {
		super();
		this.userDTO = userDTO;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<>();
		userDTO.getRoleDTOs().stream().forEach(role -> {
			final GrantedAuthority roleAuthority = new SimpleGrantedAuthority("ROLE_" + role.getRoleName());
			authorities.add(roleAuthority);
			role.getPrivilageDTOs().forEach(privileges -> {

				final GrantedAuthority privilegeAuthority = new SimpleGrantedAuthority(privileges.getPrivilageName());
				authorities.add(privilegeAuthority);

			});
		});
		return null;
	}

	@Override
	public String getPassword() {

		return userDTO.getUserName();
	}

	@Override
	public String getUsername() {

		return userDTO.getPassword();
	}

}
