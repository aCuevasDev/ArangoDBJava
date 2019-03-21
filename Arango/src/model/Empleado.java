package model;

import com.arangodb.entity.DocumentField;
import com.arangodb.entity.DocumentField.Type;

import model.dto.DepartamentoDTO;
import model.dto.EmpleadoDTO;

public class Empleado implements IKeyable {

	private String nombre;
	private String apellidos;
	@DocumentField(Type.KEY)
	private String username;
	private String contrasenya;
	private boolean jefe;
	private DepartamentoDTO departamento;

	public Empleado(String nombre, String apellidos, String username, String contrasenya, DepartamentoDTO departamento) {
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.username = username;
		this.contrasenya = contrasenya;
		this.departamento = departamento;
		this.jefe = false;
	}

	public Empleado(String nombre, String apellidos, String username, String contrasenya) {
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.username = username;
		this.contrasenya = contrasenya;
		this.departamento = null;
		this.jefe = false;
	}
	
	public Empleado(EmpleadoDTO empleado, DepartamentoDTO departamento) {
		this.nombre = empleado.getNombre();
		this.apellidos = empleado.getApellidos();
		this.username = empleado.getUsername();
		this.contrasenya = empleado.getContrasenya();
		this.jefe = empleado.isJefe();
		this.departamento = departamento;
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

	public DepartamentoDTO getDepartamento() {
		return departamento;
	}

	public void setDepartamento(DepartamentoDTO departamento) {
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
