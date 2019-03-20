package model;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import com.arangodb.entity.DocumentField;
import com.arangodb.entity.DocumentField.Type;

import model.dto.EmpleadoDTO;

public class Incidencia implements IKeyable {

	@DocumentField(Type.KEY)
	private Integer id;
	private EmpleadoDTO origen;
	private EmpleadoDTO destino;
	private String titulo;
	private String descripcion;
	private Date fechaInicio;
	private Date fechaFin;

	public Incidencia() {
	}

	public Incidencia(EmpleadoDTO origen, EmpleadoDTO destino, String titulo, String descripcion) {
		super();
		this.origen = origen;
		this.destino = destino;
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.fechaInicio = new Date();
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
		return String.valueOf(id);
		// TODO Cuevas: String.valueOf(id) daba un error Response: 400, Error: 1221 -
		// illegal document key
	}

	public EmpleadoDTO getOrigen() {
		return origen;
	}

	public void setOrigen(EmpleadoDTO origen) {
		this.origen = origen;
	}

	public EmpleadoDTO getDestino() {
		return destino;
	}

	public void setDestino(EmpleadoDTO destino) {
		this.destino = destino;
	}

}
