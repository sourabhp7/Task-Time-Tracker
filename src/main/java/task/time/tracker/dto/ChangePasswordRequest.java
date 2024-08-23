package task.time.tracker.dto;

import java.io.Serializable;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@NotBlank(message = "password is Required")
	private String password;

	@NotBlank(message = "confirmpassword")
	private String confirmpassword;

	@NotBlank(message = "token is Required")
	private String token;

}
