package model;

import java.util.Set;

import com.arangodb.entity.DocumentField;
import com.arangodb.entity.DocumentField.Type;

public class Departamento implements IKeyable {
	
	@DocumentField(Type.KEY)
	private String nombre;
	private Empleado jefe;
	private Set<Empleado> empleados;
	
	public Departamento() {}
	
	public Departamento(String nombre, Empleado jefe, Set<Empleado> empleados) {
		this.nombre = nombre;
		this.jefe = jefe;
		this.empleados = empleados;
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Empleado getJefe() {
		return jefe;
	}
	public void setJefe(Empleado jefe) {
		this.jefe = jefe;
	}
	public Set<Empleado> getEmpleados() {
		return empleados;
	}
	public void setEmpleados(Set<Empleado> empleados) {
		this.empleados = empleados;
	}
	public boolean add(Empleado empleado) {
		return this.empleados.add(empleado);
	}
	public boolean remove(Empleado empleado) {
		return this.empleados.remove(empleado);
	}

	@Override
	public String getKey() {
		return nombre;
	}

}
