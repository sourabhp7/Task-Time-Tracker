package task.time.tracker.exception;

public class DataAlreadyExistException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private ApiError apiError;

	public DataAlreadyExistException(String message) {
		super(message);
	}

	public DataAlreadyExistException(ApiError apiError) {
		super();
		this.apiError = apiError;
	}

	public ApiError getApiError() {
		return apiError;
	}

	public void setApiError(ApiError apiError) {
		this.apiError = apiError;
	}
}
