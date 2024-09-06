package task.time.tracker.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PermissionRequest {

	private Long id;
	
	@NotBlank(message = "Please Enter PermissionName")
	private String permissionName;
}
