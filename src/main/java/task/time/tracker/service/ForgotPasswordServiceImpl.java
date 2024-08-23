package task.time.tracker.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import task.time.tracker.dto.ChangePasswordRequest;
import task.time.tracker.dto.ForgotPasswordRequest;
import task.time.tracker.exception.ResourceNotFound;
import task.time.tracker.model.ForgotPassword;
import task.time.tracker.model.UserEntity;
import task.time.tracker.repository.ForgotPasswordRepository;
import task.time.tracker.repository.UserEntityRepository;
import task.time.tracker.utils.JwtUtils;

@Service
public class ForgotPasswordServiceImpl implements ForgotPasswordService {

	@Autowired
	private ForgotPasswordRepository forgotPasswordRepository;

	@Autowired
	private JwtUtils jwtUtlis;

	@Autowired
	private UserEntityRepository userEntityRepository;

	@Autowired
	private PasswordEncoder bcryptEncoder;

	@Override
	public void createForgotPasswordRequest(Long userId, String token, Date expireAt) {

		ForgotPassword forgotPassword = new ForgotPassword();
		forgotPassword.setUserId(userId);
		forgotPassword.setToken(token);
		forgotPasswordRepository.save(forgotPassword);
	}

	@Override
	public void changePassword(ChangePasswordRequest changePasswordRequest) {
		// TODO Auto-generated method stub

		String token = (changePasswordRequest.getToken());
		if (!jwtUtlis.isTokenExpired(token)) {
			if (changePasswordRequest.getPassword().equals(changePasswordRequest.getConfirmpassword())) {
				String username = jwtUtlis.extractUsernameFromToken(token);
				Optional<UserEntity> userEntitys = userEntityRepository.findByUserName(username);
				UserEntity userEntity = userEntitys.get();
				userEntity.setPassword(bcryptEncoder.encode(changePasswordRequest.getPassword()));
				userEntityRepository.save(userEntity);

			}
		} else {
			ForgotPassword forgotPasswordRequest = forgotPasswordRepository.getByTokenOrderByIdDesc(token)
					.orElseThrow(() -> new ResourceNotFound("Invalid Request"));
			forgotPasswordRequest.setIsActive(false);
			throw new ResourceNotFound("Reset the password time out");
		}
	}

}
