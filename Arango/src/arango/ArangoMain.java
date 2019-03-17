package arango;

import java.util.List;
import java.util.Set;

import controller.Controller;
import model.Departamento;
import model.Empleado;

public class ArangoMain {

	public static void main(String[] args) {
		crearDepartamento();
	}
	
	public static void login() {
		String username = InputAsker.askString("Introduce tu username");
		String contrasenya = InputAsker.askString("Introduce tu contrasenya");
		if(Controller.getInstance().login(username, contrasenya)) {
			System.out.println("Bienvenido");
		}else {
			System.out.println("Usuario o contraseña incorrectos");
		}
	}
	
	private static void crearDepartamento() {
		Departamento newDepartamento = new Departamento();
        newDepartamento.setNombre(InputAsker.askString("Introduce el nombre del departamento: "));
        // TODO buscar solo los empleados sin departamento??
        List<Empleado> empleados = Controller.getInstance().getAllUsers();
        int eleccion = 	InputAsker.askElementList("Escoje al jefe", empleados);
        newDepartamento.setJefe(eleccion == 0 ? null : empleados.get(eleccion - 1));
        Set<Empleado> empleadosDelDepartamento = InputAsker.generarListaApartirDeListaSinRepetidos("Introduce el empleado a añadir a la lista", empleados, Empleado.class);
        newDepartamento.setEmpleados(empleadosDelDepartamento);
        Controller.getInstance().crearDepartamento(newDepartamento);
        System.out.println("Departamento creado correctamente!!");
        //TODO poner el empleado como que es jefe y añadir al departamento
	}
}
