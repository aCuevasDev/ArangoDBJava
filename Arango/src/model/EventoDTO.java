package model;

import java.util.Date;

import com.arangodb.entity.DocumentField;
import com.arangodb.entity.DocumentField.Type;

import persistence.ArangoUtils.IKeyable;

public class EventoDTO implements IKeyable {

	public enum Tipo {
		LOGIN, CREACION_INCIDENCIA, CONSULTA_INCIDENCIA, SOLUCION_INCIDENCIA;
	}
	
	
	@DocumentField(Type.KEY)
	private Integer id;
	private Tipo tipo;
	private Date fecha;
	private String empleado;
	
	public EventoDTO() {}
	
	public EventoDTO(Tipo tipo, String empleado) {
		this.tipo = tipo;
		this.fecha = new Date();
		this.empleado = empleado;
	}
	public Tipo getTipo() {
		return tipo;
	}
	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public String getEmpleado() {
		return empleado;
	}
	public void setEmpleado(String empleado) {
		this.empleado = empleado;
	}

	@Override
	public String getKey() {
		return String.valueOf(id);
	}
	
	
	
	
	

}
