package task.time.tracker.service;

import java.util.Date;

import task.time.tracker.dto.ChangePasswordRequest;
import task.time.tracker.dto.ForgotPasswordRequest;

public interface ForgotPasswordService {

	public void createForgotPasswordRequest(Long userId, String token, Date expireAt);
	
	public void changePassword(ChangePasswordRequest changePasswordRequest);
}
