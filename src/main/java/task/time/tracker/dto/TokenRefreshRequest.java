package task.time.tracker.dto;

import jakarta.validation.constraints.NotBlank;

public class TokenRefreshRequest {
	@NotBlank(message = "Please Enter The Refresh Token")
	private String refreshToken;

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
}
