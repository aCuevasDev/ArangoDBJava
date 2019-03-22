package model.dto;

import java.util.Date;

import com.arangodb.entity.DocumentField;
import com.arangodb.entity.DocumentField.Type;

import model.IKeyable;
import model.Incidencia;

public class IncidenciaDTO implements IKeyable {

	@DocumentField(Type.KEY)
	private Integer id;
	private String origen;
	private String destino;
	private String titulo;
	private String descripcion;
	private Date fechaInicio;
	private Date fechaFin;

	public IncidenciaDTO() {}

	public IncidenciaDTO(String origen, String destino, String titulo, String descripcion) {
		this.origen = origen;
		this.destino = destino;
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.fechaInicio = new Date();
	}

	public IncidenciaDTO(Incidencia i) {
		this.origen = i.getOrigen().getUsername();
		this.destino = i.getDestino().getUsername();
		this.titulo = i.getTitulo();
		this.descripcion = i.getDescripcion();
		this.fechaInicio = i.getFechaInicio();
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

}
