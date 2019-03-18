package model;

import java.util.Date;

import com.arangodb.entity.DocumentField;
import com.arangodb.entity.DocumentField.Type;

public class Evento implements IKeyable{

	public enum Tipo {
		LOGOUT, LOGIN, CREACION_INCIDENCIA, CONSULTA_INCIDENCIA, FIN_INCIDENCIA;
	}
	
	@DocumentField(Type.KEY)
	private int id;
	private Tipo tipo;
	private Date fecha;
	private Empleado empleado;
	
	public Evento() {}
	
	public Evento(Tipo tipo, Empleado empleado) {
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
	public Empleado getEmpleado() {
		return empleado;
	}
	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}

	@Override
	public String getKey() {
		return String.valueOf(id);
	}
	
	
	
	
	

}
