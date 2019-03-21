package arango;

import java.util.Arrays;
import java.util.List;

import controller.Controller;
import exception.FatalException;
import exception.InvalidException;
import model.dto.DepartamentoDTO;
import model.dto.EmpleadoDTO;

public class ArangoMain {

	private static Controller controller;
	
	/**
	 * <pre>
	 *  TODO Cuevas: No me gusta nada tener estos m�todos aqu�, yo los pondr�a en el Controller. Pero como quer�is vosotros que es pijada m�a. Tambi�n cambiar�a el nombre del paquete :*
	 * </pre>
	 */

	public static void main(String[] args) {
		try {
			controller = Controller.getInstance();
			authMenu();
		} catch (FatalException e) {
			System.err.println(e.getMessage());
		} finally {
			controller.closeConexion();
		}
	}
	
	
	private static void authMenu() {
		boolean cont = true;
		int option;
		do {
			option = InputAsker.askElementList("", Arrays.asList("Login","Registrarse","Salir"));
			switch(option) {
				case 0: login(); break;
				case 1: register(); break;
				case 2: 
					System.out.println("Hasta la proxima!");
					cont = false;
					break;
				default: System.err.println("Opcion invalida");
			}
		} while(cont);
	}
	
	private static void mainMenu() {
		// TODO Implementation
	}
	
	private static void register() {
		// TODO Implementation
	}


	private static void login() throws InvalidException {
		String username = InputAsker.askString("Introduce tu username");
		String contrasenya = InputAsker.askString("Introduce tu contrasenya");
		System.out.println(Controller.getInstance().login(username, contrasenya));
	}

	private static DepartamentoDTO crearDepartamento() {
		DepartamentoDTO newDepartamento = new DepartamentoDTO();
		newDepartamento.setNombre(InputAsker.askString("Introduce el nombre del departamento: "));
		List<EmpleadoDTO> empleados = Controller.getInstance().getAllUsers();
		int eleccion = InputAsker.askElementList("Escoje al jefe", empleados);
		newDepartamento.setJefe(eleccion == 0 ? null : empleados.get(eleccion - 1).getNombre());
		Controller.getInstance().crearDepartamento(newDepartamento);
		return newDepartamento;
		// TODO poner el empleado como que es jefe y añadir al departamento
		// TODO Cuevas: todas las tildes y � me salen raras, hay que a�adir UTF-8
	}
}
