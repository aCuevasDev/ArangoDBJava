package model;

import java.util.Date;

import com.arangodb.entity.DocumentField;
import com.arangodb.entity.DocumentField.Type;

import persistence.ArangoUtils.ArangoDocument;

/**
 * Representa un evento en el programa, estos pueden ser de distintos tipos, para ver las opciones vea el enum EventoDTO.Tipo.
 * Implementa Comparable, considera mayor al evento mas nuevo.
 * Es un ArangoDocument por lo que puede persistir en una base de datos ArangoDB.
 * 
 * @author razz97
 * @author acuevas
 * @author movip88
 */
public class EventoDTO implements ArangoDocument, Comparable<EventoDTO> {

	/**
	 * Representa los tipos de eventos posibles en la aplicacion:
	 * 1. Login: Un usuario ha iniciado sesion en la aplicacion.
	 * 2. Creacion incidencia: Se ha creado una nueva incidencia a resolver.
	 * 3. Solucion incidencia: Se ha solucionado una incidencia.
	 * 
	 * @author razz97
	 * @author acuevas
	 * @author movip88
	 */
	public enum Tipo {
		LOGIN, CREACION_INCIDENCIA, SOLUCION_INCIDENCIA;
	}
	
	@DocumentField(Type.KEY)
	private Integer id;
	private Tipo tipo;
	private Date fecha;
	private String empleado;
	
	public EventoDTO() {}
	
	/**
	 * Construye un evento a partir de un tipo y el empleado causante del evento.
	 * @param tipo
	 * @param empleado
	 */
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

	@Override
	public int compareTo(EventoDTO o) {
		return fecha.compareTo(o.fecha);
	}
}
