package model;

import com.arangodb.entity.DocumentField;
import com.arangodb.entity.DocumentField.Type;
import persistence.IKeyable;

public class EmpleadoDTO implements IKeyable {

	private String nombre;
	private String apellidos;
	
	@DocumentField(Type.KEY)
	private String username;
	private String contrasenya;
	private boolean jefe;
	private String departamento;
	
	public EmpleadoDTO(String nombre, String apellidos, String username, String contrasenya, String departamento) {
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.username = username;
		this.contrasenya = contrasenya;
		this.departamento = departamento;
		this.jefe = false;
	}

	public EmpleadoDTO(String nombre, String apellidos, String username, String contrasenya) {
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.username = username;
		this.contrasenya = contrasenya;
		this.departamento = null;
		this.jefe = false;
	}
	
	public EmpleadoDTO(String username) {
		this.username = username;
	}

	public EmpleadoDTO() {}

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

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	public boolean isJefe() {
		return jefe;
	}

	public void setJefe(boolean jefe) {
		this.jefe = jefe;
	}

	public String getNombreCompleto() {
		return new StringBuilder(apellidos).append(" ,").append(nombre).toString();
	}
	
	@Override
	public String toString() {
		return getNombreCompleto();
	}

	@Override
	public String getKey() {
		return username;
	}

}
