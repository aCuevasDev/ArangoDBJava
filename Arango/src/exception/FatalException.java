package exception;

public class FatalException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private String message = "There was a problem when accessing the database.";

	@Override
	public String getMessage() {
		return message;
	}
	
	
	
}
