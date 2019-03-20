package model.dto;

import com.arangodb.entity.DocumentField;
import com.arangodb.entity.DocumentField.Type;

import model.Departamento;
import model.IKeyable;

public class DepartamentoDTO implements IKeyable {

	@DocumentField(Type.KEY)
	private String nombre;
	private String jefe;

	public DepartamentoDTO(Departamento departamento) {
		this.nombre = departamento.getNombre();
		this.jefe = departamento.getJefe().getNombre();
	}

	public DepartamentoDTO() {
	}

	public DepartamentoDTO(String nombre, String jefe) {
		this.nombre = nombre;
		this.jefe = jefe;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getJefe() {
		return jefe;
	}

	public void setJefe(String jefe) {
		this.jefe = jefe;
	}

	@Override
	public String getKey() {
		return nombre;
	}

}
