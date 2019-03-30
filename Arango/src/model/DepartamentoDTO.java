package model;

import com.arangodb.entity.DocumentField;
import com.arangodb.entity.DocumentField.Type;
import persistence.IKeyable;

public class DepartamentoDTO implements IKeyable {

	@DocumentField(Type.KEY)
	private String nombre;
	private String jefe;

	public DepartamentoDTO(String nombre) {
		this.nombre = nombre;
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
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
		DepartamentoDTO other = (DepartamentoDTO) obj;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		return true;
	}

	@Override
	public String getKey() {
		return nombre;
	}
	
	@Override
	public String toString() {
		return nombre;
	}
}
