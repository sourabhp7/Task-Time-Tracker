package task.time.tracker.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleRequest {

	private Long id;

	@NotBlank(message = "Please Enter RoleName")
	private String roleName;
}
