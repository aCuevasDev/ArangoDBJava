package model;

public class Empleado extends Codificable {
	
	private String nombre;
	private String apellidos;
	private String username;
	private String contrasenya;
	private boolean jefe;
	private Departamento departamento;
	
	public Empleado(String nombre, String apellidos, String username, String contrasenya, Departamento departamento) {
		super();
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.username = username;
		this.contrasenya = contrasenya;
		this.departamento = departamento;
		this.jefe = false;
	}
	
	public Empleado(String nombre, String apellidos, String username, String contrasenya) {
		super();
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.username = username;
		this.contrasenya = contrasenya;
		this.departamento = null;
		this.jefe = false;
	}
	
	public Empleado() {
		super();
	}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	public String getContrasenya() {
		return contrasenya;
	}
	public void setContrasenya(String contrasenya) {
		this.contrasenya = contrasenya;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Departamento getDepartamento() {
		return departamento;
	}
	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}
	public boolean isJefe() {
		return jefe;
	}
	public void setJefe(boolean jefe) {
		this.jefe = jefe;
	}

}
