package model;

import com.arangodb.entity.BaseDocument;
import com.arangodb.entity.DocumentField;
import com.arangodb.entity.DocumentField.Type;

public class Codificable {
	
	@DocumentField(Type.KEY)
	private String codigo;
	private static long index;
	
	public Codificable() {
		codigo = getClass().getSimpleName().substring(0,3) + index++;
	}
	
	public String getKey() {
		return codigo;
	}
	
	public String getCodigo() {
		return codigo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
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
		Codificable other = (Codificable) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
	
	
}
