package task.time.tracker.dto;

import java.time.LocalDate;

import org.hibernate.validator.constraints.URL;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {

	private Long id;

	@NotBlank(message = "name is mandotory")
	@Size(max = 100, message = "Name must be less than 100 characters")
	private String name;

	@NotBlank(message = "Email is mandatory")
	@Email(message = "Email should be valid")
	@Size(max = 100, message = "Email must be less than 100 characters")
	private String emailId;

	@NotNull(message = "Date of birth is mandatory")
	@Past(message = "Date of birth must be a past date")
	private LocalDate dateOfBirth;

	@NotBlank(message = "Mobile number is mandatory")
	@Pattern(regexp = "(^[789]\\d{9}$)",message = "Mobile number should start with 7, 8, or 9 and have 10 digits")
	private String mobileNo;

	@Pattern(regexp = "^(A|B|AB|O)[+-]$", message = "Blood group must be one of A+, A-, B+, B-, AB+, AB-, O+, or O-")
	private String bloodGroup;

	@Size(max = 255, message = "Address must be less than 255 characters")
	private String address;

	@Size(max = 100, message = "City must be less than 100 characters")
	private String city;

	@NotBlank(message = "Registration number is mandatory")
	@Size(max = 50, message = "Registration number must be less than 50 characters")
	private String resgistrationNo;

	@URL(message = "Image URL should be valid")
	private String imageUrl;

	private Boolean isActive;

}
