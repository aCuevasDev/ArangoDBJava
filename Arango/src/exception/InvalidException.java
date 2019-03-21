package exception;

public class InvalidException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public enum Tipo {
		
		INVALID_CREDENTIALS("Invalid credentials."), 
		ALREADY_EXISTS("User already exists.");
		
		private String message;
		
		Tipo(String message) { this.message = message; }
	}
	
	private String message;
	
	public InvalidException(Tipo tipo) {
		this.message = tipo.message;
	}
	
	@Override
	public String getMessage() {
		return message;
	}
	

}
