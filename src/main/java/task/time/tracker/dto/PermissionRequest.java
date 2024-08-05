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
public class PermissionRequest {

	private Long id;
	
	@NotBlank(message = "Please Enter PermissionName")
	@NotEmpty(message = "Please Enter PermissionName")
	@NotNull(message = "Please Enter PermissionName")
	private String permissionName;
}