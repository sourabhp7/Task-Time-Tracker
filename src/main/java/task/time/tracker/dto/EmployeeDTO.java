package task.time.tracker.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {

	private Long id;

	private String name;

	private String emailId;

	private LocalDate dateOfBirth;

	private String mobileNo;

	private String bloodGroup;

	private String address;

	private String city;

	private String resgistrationNo;

	private String imageUrl;
	
	private Boolean isActive;

}
