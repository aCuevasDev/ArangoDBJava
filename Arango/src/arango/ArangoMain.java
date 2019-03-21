package arango;

import java.util.Arrays;
import java.util.List;

import com.arangodb.ArangoDBException;

import controller.Controller;
import exception.InvalidException;
import model.dto.DepartamentoDTO;
import model.dto.EmpleadoDTO;

public class ArangoMain {

	private static Controller controller;

	public static void main(String[] args) {
		try {
			controller = Controller.getInstance();
			authMenu();
		} catch (ArangoDBException e) {
			System.err.println(e.getMessage());
		} finally {
			controller.closeConexion();
		}
	}
	
	
	private static void authMenu() {
		int option;
		do {
			option = InputAsker.askElementList("Selecciona una opcion:", Arrays.asList("Login","Salir"));
			switch(option) {
				case 0: login(); break;
				case 1: System.out.println("Hasta la proxima!"); break;
				default: System.err.println("Opcion invalida");
			}
		} while(option != 1);
	}
	
	// @Bou
	private static void mainMenu() {
		int option;
		do {
			option = InputAsker.askElementList("Selecciona una opcion: ", Arrays.asList("Login","Registrarse","Salir"));
			try {
				switch(option) {
					case 0: login(); break;
					case 1: register(); break;
					case 2: 
						System.out.println("Hasta la proxima!");
						break;
					default: System.err.println("Opcion invalida");
				}
			} catch (InvalidException e) {
				System.err.println(e.getMessage());
			}
		} while(option != 2);
	}
	
	// @Vives
	private static void register() {
		// TODO Implementation (solo jefe)
	}
	
	// @Cuevas
	private static void updateEmpleado() {
		// TODO implementation (todos)
	}
	
	// @Bou
	private static void updateDepartamento() {
		// TODO implementation (solo jefe)
	}
	
	// @Vives
	private static void deleteEmpleado() {
		// TODO implementation (solo jefe)
	}
	
	// @Cuevas
	private static void solucionarIncidencia() {
		// TODO implementation (todas)
	}

	// @Bou
	private static void listarIncidencias() {
		// TODO implementation (jefe todas departamento, empleado solo suyas)
	}
	
	
	// @Vives
	private static void mostrarRanking() {
		// TODO implementation solo jefe, incidencias urgentes 2 puntos otras 1 punto.
	}
	
	// @Cuevas
	private static void crearIncidencia() {
		// TODO implementation (solo jefe)		
	}
	
	private static void login() {
		try {
			String username = InputAsker.askString("Introduce tu username");
			String contrasenya = InputAsker.askString("Introduce tu contrasenya");
			controller.login(username, contrasenya);
			System.out.println("Bienvenido, " + username);
			mainMenu();
		} catch (InvalidException e) {
			System.err.println(e.getMessage());
		}
	}

	private static DepartamentoDTO crearDepartamento() throws InvalidException {
		DepartamentoDTO newDepartamento = new DepartamentoDTO();
		newDepartamento.setNombre(InputAsker.askString("Introduce el nombre del departamento: "));
		List<EmpleadoDTO> empleados = Controller.getInstance().getAllUsers();
		int eleccion = InputAsker.askElementList("Escoje al jefe", empleados);
		newDepartamento.setJefe(eleccion == 0 ? null : empleados.get(eleccion - 1).getNombre());
		Controller.getInstance().crearDepartamento(newDepartamento);
		return newDepartamento;
		// TODO (Solo jefes)
		// TODO poner el empleado como que es jefe y añadir al departamento
		// TODO Cuevas: todas las tildes y ñ me salen raras, hay que a�adir UTF-8
	}
}
