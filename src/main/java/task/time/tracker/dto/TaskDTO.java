package task.time.tracker.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTO {

	private Long id;

	@NotBlank(message = "Please Enter the Task Name")
	private String taskName;

	private String notes;

	@NotBlank(message = "Please Enter the Deadline Date")
	private String deadLineDate;

	private String status;

	@NotBlank(message = "Please Enter the Estimated Time")
	private String estimatedTime;

	@NotNull(message = "priorityId is requried")
	private Long priorityId;

	private List<MultipartFile> files;
}
