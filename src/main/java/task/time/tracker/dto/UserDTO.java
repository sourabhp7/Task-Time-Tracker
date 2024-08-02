package task.time.tracker.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	private String userName;
	
	private String password;

	private Set<RoleDTO> roleDTOs;

}
