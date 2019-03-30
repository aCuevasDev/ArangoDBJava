package model;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.arangodb.entity.DocumentField;
import com.arangodb.entity.DocumentField.Type;

import persistence.ArangoUtils.IKeyable;

public class IncidenciaDTO implements IKeyable {

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

	public IncidenciaDTO() {
	}

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
		return "Titulo: " + titulo + ", descripcion: " + descripcion + ", origen: " + origen + ", destino: " + destino +
				", inicio: " + (fechaInicio != null ? formatter.format(fechaInicio) : "-") + ", fin: " + (fechaFin != null ? formatter.format(fechaFin) : "-");
	}

	/**
	 * @return the urgente
	 */
	public boolean isUrgente() {
		return urgente;
	}

	/**
	 * @param urgente the urgente to set
	 */
	public void setUrgente(boolean urgente) {
		this.urgente = urgente;
	}

}
