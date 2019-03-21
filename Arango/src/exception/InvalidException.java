package exception;

public class InvalidException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public enum Tipo {
		
		INVALID_CREDENTIALS("Usuario o contrasena incorrectos."), 
		USER_EXISTS("Nombre de usuario ya existe."),
		DEPARTMENT_EXISTS("Departamento ya existe");
		
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