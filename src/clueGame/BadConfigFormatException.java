package clueGame;

public class BadConfigFormatException extends Exception {
	// default constructor
	public BadConfigFormatException() {};
	
	// constructor with error message
	public BadConfigFormatException(String message) {
		super(message);
	}
}
