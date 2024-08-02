package task.time.tracker.exception;

public class ResourceNotFound extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ApiError apiError;

	public ResourceNotFound(String message) {
		super(message);
	}

	public ResourceNotFound(ApiError apiError) {
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
