/*
 * 
 */
package task.time.tracker.constant;



// TODO: Auto-generated Javadoc
/**
 * The Enum Operation.
 */
public enum Operation {

	/** The add. */
	ADD("ADD"),

	/** The modified. */
	MODIFIED("MODIFED"),

	/** The delete. */
	DELETE("DELETE");

	/** The value. */
	protected String value;

	/**
	 * Instantiates a new operation.
	 *
	 * @param value the value
	 */
	Operation(final String value) {
		this.value = value;
	}

	/**
	 * Value.
	 *
	 * @return the string
	 */
	public String value() {
		return this.value;
	}

}
