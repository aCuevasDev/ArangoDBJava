package model;

import java.util.Date;

import com.arangodb.entity.DocumentField;
import com.arangodb.entity.DocumentField.Type;

public class Incidencia implements IKeyable{

	@DocumentField(Type.KEY)
	private int id;
	private Empleado origen;
	private Empleado destino;
	private String titulo;
	private String descripcion;
	private Date fechaInicio;
	private Date fechaFin;
	
	public Incidencia() {}
	
	public Incidencia(Empleado origen, Empleado destino, String titulo, String descripcion) {
		super();
		this.origen = origen;
		this.destino = destino;
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.fechaInicio = new Date();
	}

	public Empleado getOrigen() {
		return origen;
	}

	public void setOrigen(Empleado origen) {
		this.origen = origen;
	}

	public Empleado getDestino() {
		return destino;
	}

	public void setDestino(Empleado destino) {
		this.destino = destino;
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
	}
	
	
}
