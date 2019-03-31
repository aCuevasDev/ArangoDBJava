package model;

import com.arangodb.entity.DocumentField;
import com.arangodb.entity.DocumentField.Type;

import persistence.ArangoUtils.ArangoDocument;

/**
 * Representa un empleado de la empresa y se usa tambien como usuario en la aplicacion.
 * Contiene: nombre, apellidos, username, contrasenya, si es jefe y el departamento al que pertenece.
 * Es un ArangoDocument por lo que puede persistir en una base de datos ArangoDB.
 * 
 * @author razz97
 * @author acuevas
 * @author movip88
 */
public class EmpleadoDTO implements ArangoDocument {

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
		return new StringBuilder(apellidos).append(", ").append(nombre).toString();
	}
	
	@Override
	public String toString() {
		return "Nombre: " + nombre + ", apellidos: " + apellidos + ", username: " + username + ", jefe: " + jefe + ", departamento: " + departamento;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EmpleadoDTO other = (EmpleadoDTO) obj;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	@Override
	public String getKey() {
		return username;
	}

}
