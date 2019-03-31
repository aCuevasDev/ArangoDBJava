package model;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.arangodb.entity.DocumentField;
import com.arangodb.entity.DocumentField.Type;

import persistence.ArangoUtils.ArangoDocument;

/**
 * Representa una incidencia en la empresa, los empleados son los responsables de crearlas y solucionarlas.
 * No tienen clave de negocio, pues no hay dos incidencias iguales, se usa el id generado por la base de datos.
 * Es un ArangoDocument por lo que puede persistir en una base de datos ArangoDB.
 * 
 * @author razz97
 * @author acuevas
 * @author movip88
 */
public class IncidenciaDTO implements ArangoDocument {

	@DocumentField(Type.KEY)
	private String id;
	private String origen;
	private String destino;
	private String titulo;
	private String descripcion;
	private Date fechaInicio;
	private Date fechaFin;
	private boolean urgente;
	private static SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy");

	public IncidenciaDTO() {}
	
	/**
	 * Crea una incidencia a partir de un id.
	 * @param id
	 */
	public IncidenciaDTO(int id) {
		this.id = String.valueOf(id);
	}

	/**
	 * Crea una incidencia con todos sus propiedades.
	 * @param origen - empleado creador de la incidencia.
	 * @param destino - empleado que debe solucionarla.
	 * @param titulo - titulo de la incidencia.
	 * @param descripcion - descripcion del problema.
	 * @param urgente - si es urgente (true) o no (fale).
	 */
	public IncidenciaDTO(String origen, String destino, String titulo, String descripcion, boolean urgente) {
		this.origen = origen;
		this.destino = destino;
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.fechaInicio = new Date();
		this.urgente = urgente;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	@Override
	public String getKey() {
		return id;
	}

	public String getOrigen() {
		return origen;
	}

	public void setOrigen(String origen) {
		this.origen = origen;
	}

	public String getDestino() {
		return destino;
	}

	public void setDestino(String destino) {
		this.destino = destino;
	}

	@Override
	public String toString() {
		return (urgente ? "URGENTE - " : "") + "Titulo: " + titulo + ", descripcion: " + descripcion + ", origen: " + origen + ", destino: " + destino +
				", inicio: " + (fechaInicio != null ? formatter.format(fechaInicio) : "-") + ", fin: " + (fechaFin != null ? formatter.format(fechaFin) : "-");
	}
	
	public boolean isUrgente() {
		return urgente;
	}
	
	public void setUrgente(boolean urgente) {
		this.urgente = urgente;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isSolucionada() {
		return fechaFin != null;
	}

}
