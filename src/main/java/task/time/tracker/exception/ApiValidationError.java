package task.time.tracker.exception;

import java.io.Serializable;

public class ApiValidationError extends ApiSubError implements Serializable {

	private static final long serialVersionUID = 1L;
	private String entity;
	private String field;
	private Object rejectedValue;
	private String message;

	ApiValidationError(String entity, String message) {
		this.entity = entity;
		this.message = message;
	}

	public String getEntity() {
		return entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public Object getRejectedValue() {
		return rejectedValue;
	}

	public void setRejectedValue(Object rejectedValue) {
		this.rejectedValue = rejectedValue;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ApiValidationError() {
		super();
		// TODO Auto-generated constructor stub
	}

}
