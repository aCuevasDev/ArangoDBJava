package model;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import com.arangodb.entity.DocumentField;
import com.arangodb.entity.DocumentField.Type;

public class Incidencia implements IKeyable {

	@DocumentField(Type.KEY)
	private int id;
//	private Empleado origen;
//	private Empleado destino;
	private String titulo;
	private String descripcion;
	private Date fechaInicio;
	private Date fechaFin;
	private Set<Empleado> empleados = new LinkedHashSet<>();

	public Incidencia() {
	}

	public Incidencia(Empleado origen, Empleado destino, String titulo, String descripcion) {
		super();
//		this.origen = origen;
//		this.destino = destino;
		empleados.add(origen);
		empleados.add(destino);
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

	/**
	 * @return the empleados
	 */
	public Set<Empleado> getEmpleados() {
		return empleados;
	}

	/**
	 * @param empleados the empleados to set
	 */
	public void setEmpleados(Set<Empleado> empleados) {
		this.empleados = empleados;
	}

}
