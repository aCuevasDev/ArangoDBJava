package arango;

import java.util.List;
import java.util.Set;

import controller.Controller;
import model.Departamento;
import model.Empleado;

public class ArangoMain {

	// Cuevas: No me gusta nada tener estos métodos aquí, yo los pondría en el
	// Controller. Pero como queráis vosotros que es pijada mía.

	public static void main(String[] args) {
		insertarEmpleado();
		crearDepartamento();
		Controller.getInstance().closeConexion();
	}

	private static void insertarEmpleado() {
		System.out.println(
				Controller.getInstance().crearEmpleado(new Empleado("zzz", "zzz", "zz", "zzz", new Departamento())));
	}

	public static void login() {
		String username = InputAsker.askString("Introduce tu username");
		String contrasenya = InputAsker.askString("Introduce tu contrasenya");
		if (Controller.getInstance().login(username, contrasenya)) {
			System.out.println("Bienvenido");
		} else {
			System.out.println("Usuario o contraseÃ±a incorrectos");
		}
	}

	private static void crearDepartamento() {
		Departamento newDepartamento = new Departamento();
		newDepartamento.setNombre(InputAsker.askString("Introduce el nombre del departamento: "));
		// TODO buscar solo los empleados sin departamento??
		List<Empleado> empleados = Controller.getInstance().getAllUsers();
		int eleccion = InputAsker.askElementList("Escoje al jefe", empleados);
		newDepartamento.setJefe(eleccion == 0 ? null : empleados.get(eleccion - 1));
		Set<Empleado> empleadosDelDepartamento = InputAsker.generarListaApartirDeListaSinRepetidos(
				"Introduce el empleado a aÃ±adir a la lista", empleados, Empleado.class);
		newDepartamento.setEmpleados(empleadosDelDepartamento);
		Controller.getInstance().crearDepartamento(newDepartamento);
		System.out.println("Departamento creado correctamente!!");
		// TODO poner el empleado como que es jefe y aÃ±adir al departamento
	}
}
