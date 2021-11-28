package by.ifanbel.data.database.services.exceptions;

/**
 * The exception occures if non-existing Heterostructure object is requested from the database.
 */
public class NoSuchHeterostructureException extends Exception {
	private String message;

	public NoSuchHeterostructureException(String sampleNumber) {
		message = "No heterostructure named as \"".concat(sampleNumber)
				.concat("\" was found in the database");
	}

	@Override
	public String toString() {
		return message;
	}
}
