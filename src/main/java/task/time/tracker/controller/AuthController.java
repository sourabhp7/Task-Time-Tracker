package task.time.tracker.controller;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import task.time.tracker.config.AppSettings;
import task.time.tracker.dto.ChangePasswordRequest;
import task.time.tracker.dto.ForgotPasswordRequest;
import task.time.tracker.dto.JwtResponse;
import task.time.tracker.dto.LoginRequest;
import task.time.tracker.dto.SuccessResponse;
import task.time.tracker.dto.TokenRefreshRequest;
import task.time.tracker.dto.TokenRefreshResponse;
import task.time.tracker.dto.UserDTO;
import task.time.tracker.exception.ResourceNotFound;
import task.time.tracker.exception.TokenRefreshException;
import task.time.tracker.model.RefreshToken;
import task.time.tracker.service.EmailService;
import task.time.tracker.service.ForgotPasswordService;
import task.time.tracker.service.RefreshTokenServiceImpl;
import task.time.tracker.service.UserEntityService;
import task.time.tracker.service.UserPrincipalService;
import task.time.tracker.utils.JwtUtils;

@RestController
@RequestMapping("/auth")
public class AuthController {
	public static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private RefreshTokenServiceImpl refreshTokenService;

	@Autowired
	private EmailService emailService;

	@Autowired
	private UserEntityService userEntityService;

	@Autowired
	private AppSettings appSettings;

	@Autowired
	private ForgotPasswordService forgotPasswordService;

	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		final Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		LOGGER.info("UserName: {} .", loginRequest.getUsername());

		SecurityContextHolder.getContext().setAuthentication(authentication);

		final UserPrincipalService userDetails = (UserPrincipalService) authentication.getPrincipal();
		final String jwt = jwtUtils.generateJwtToken(userDetails.getUsername());
		final List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());
		LOGGER.info("UserType is: " + roles.get(0));

		final RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getUsername());
		return ResponseEntity.ok(new JwtResponse(jwt, "Bearer", refreshToken.getToken()));
	}

	@PostMapping("/refreshtoken")
	public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshRequest request) {
		final String requestRefreshToken = request.getRefreshToken();

		return refreshTokenService.findByToken(requestRefreshToken).map(refreshTokenService::verifyExpiration)
				.map(RefreshToken::getUserEntity).map(user -> {
					final String token = jwtUtils.generateJwtToken(user.getUserName());
					return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
				})
				.orElseThrow(() -> new TokenRefreshException(requestRefreshToken, "Refresh token is not in database!"));
	}

	@PostMapping("/forget-password")
	public ResponseEntity<?> forgetPassword(@Valid @RequestBody ForgotPasswordRequest forgotPasswordRequest) {
		UserDTO userDTO = userEntityService.findByUserName(forgotPasswordRequest.getEmail());
		if (userDTO == null) {
			throw new ResourceNotFound("Email is Not valid");
		} else {
			final String token = jwtUtils.generateJwtToken(userDTO.getUserName());
			final String frontEndUrl = appSettings.getForgotPasswordUrl();
			final String baseUrl = appSettings.getFrontendBaseUrl();
			final String url = "To confirm your account, please click here : " + baseUrl + frontEndUrl + "?token="
					+ token;
			Calendar calender = Calendar.getInstance();
			calender.add(Calendar.MINUTE, 5);
			forgotPasswordService.createForgotPasswordRequest(userDTO.getId(), token, calender.getTime());
			LOGGER.info("CONTROLLER >> ForgetPassword Requested >> Done");
			emailService.sendEmail("beherad592@gmail.com", "Forgot Password", url);
			SuccessResponse successResponse = new SuccessResponse();
			successResponse.setTimestamp(LocalDateTime.now());
			successResponse.setMessage("Password reset link sent on Registerd Email");
			successResponse.setStatus(HttpStatus.OK);
			successResponse.setData(null);
			LOGGER.info("CONTROLLER >> ForgetPassword Requested >> Successfull");
			return new ResponseEntity<>(successResponse, HttpStatus.OK);

		}
	}
	
	@PostMapping("/forgot-pass-confirm")
	public ResponseEntity<?> forgetPasswordChange(@Valid @RequestBody ChangePasswordRequest changePasswordRequest )
	 {
		LOGGER.info("CONTROLLER >> forgotPassword >> Start >>");
	    forgotPasswordService.changePassword(changePasswordRequest);
	    SuccessResponse successResponse = new SuccessResponse();
		successResponse.setTimestamp(LocalDateTime.now());
		successResponse.setMessage("Password is Upadted");
		successResponse.setStatus(HttpStatus.OK);
		successResponse.setData(null);
		LOGGER.info("CONTROLLER >> ForgetPassword Requested >> Successfull");
		return new ResponseEntity<>(successResponse, HttpStatus.OK);
	}
}
