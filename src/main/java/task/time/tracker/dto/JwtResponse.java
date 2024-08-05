package task.time.tracker.dto;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String token;
	private String type = "Bearer";
	private String refreshToken;
}
