package model.dto;

import com.arangodb.entity.DocumentField;
import com.arangodb.entity.DocumentField.Type;

import model.Empleado;
import model.IKeyable;

public class EmpleadoDTO implements IKeyable {

	private String nombre;
	private String apellidos;
	
	@DocumentField(Type.KEY)
	private String username;
	private String contrasenya;
	private boolean jefe;
	private String departamento;
	
	public EmpleadoDTO(Empleado empleado) {
		this.nombre = empleado.getNombre();
		this.apellidos = empleado.getApellidos();
		this.username = empleado.getUsername();
		this.contrasenya = empleado.getContrasenya();
		this.departamento = empleado.getDepartamento().getNombre();
		this.jefe = empleado.isJefe();
	}

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

	@Override
	public String toString() {
		return username + " " + contrasenya;
	}

	@Override
	public String getKey() {
		return username;
	}

}