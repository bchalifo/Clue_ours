package clueGame;

public class BadConfigFormatException extends RuntimeException {
	// constructors
	public BadConfigFormatException() {};
	public BadConfigFormatException(String message) {
		super(message);
	}
}
