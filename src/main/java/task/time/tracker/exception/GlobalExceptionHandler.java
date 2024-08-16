package task.time.tracker.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {

		final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
		apiError.setMessage("Missing Or Invalid Data");
		final List<ApiSubError> subErrors = new ArrayList<>();

		final BindingResult result = ex.getBindingResult();
		final List<FieldError> fieldErrors = result.getFieldErrors();

		for (final FieldError currentError : fieldErrors) {
			final ApiValidationError apiSubError = new ApiValidationError();
			apiSubError.setEntity(currentError.getObjectName());
			apiSubError.setMessage(currentError.getDefaultMessage());
			apiSubError.setField(currentError.getField());
			apiSubError.setRejectedValue(currentError.getRejectedValue());
			subErrors.add(apiSubError);
		}
		apiError.setSubErrors(subErrors);

		return buildResponseEntity(apiError);
	}

	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String error = "Malformed JSON request";
		return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, error, ex));
	}

	@ExceptionHandler(ResourceNotFound.class)
	public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFound ex) {
		final ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
		return buildResponseEntity(apiError);
	}

	@ExceptionHandler(BadCredentialsException.class)

	public ResponseEntity<Object> handleBadCredentialsException(BadCredentialsException ex) {

		ex.printStackTrace();

		return buildResponseEntity(new ApiError(HttpStatus.UNAUTHORIZED, "Authentication Failed", ex));

	}

	@ExceptionHandler(AccessDeniedException.class)

	public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex) {

		ex.printStackTrace();

		return buildResponseEntity(new ApiError(HttpStatus.FORBIDDEN, "Access Denied", ex));

	}

	@ExceptionHandler(SignatureException.class)

	public ResponseEntity<Object> handleSignatureException(SignatureException ex) {

		ex.printStackTrace();

		return buildResponseEntity(new ApiError(HttpStatus.FORBIDDEN, "JWT Signature Not Valid !", ex));

	}

	@ExceptionHandler(ExpiredJwtException.class)

	public ResponseEntity<Object> handleExpiredJwtException(ExpiredJwtException ex) {

		ex.printStackTrace();

		return buildResponseEntity(new ApiError(HttpStatus.FORBIDDEN, "JWT token already expired !", ex));

	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleGenericException(Exception ex) {
		ex.printStackTrace();
		return buildResponseEntity(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "Global Exception", ex));
	}

	private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
		return new ResponseEntity<Object>(apiError, apiError.getStatus());
	}

}
