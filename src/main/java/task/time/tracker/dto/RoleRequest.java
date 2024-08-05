package task.time.tracker.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleRequest {

	private Long id;

	@NotBlank(message = "Please Enter RoleName")
	@NotEmpty(message = "Please Enter RoleName")
	@NotNull(message = "Please Enter RoleName")
	private String roleName;
}
