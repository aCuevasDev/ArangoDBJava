package arango;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.arangodb.ArangoDBException;

import controller.Controller;
import exception.InvalidException;
import exception.InvalidException.Tipo;
import model.Empleado;
import model.dto.DepartamentoDTO;
import model.dto.EmpleadoDTO;

public class ArangoMain {

	private static Controller controller;
	// Estas listas no permiten añadir o quitar elementos on the fly
	private static final List<String> opcionesEmpleado = Arrays.asList( 
			"Actualizar empleado.", 
			"Solucionar incidencia.", 
			"Listar incidencias"
	); 
	private static final List<String> opcionesJefe = Arrays.asList(
			"Registrar empleado.", 
			"Borrar empleado.", 
			"Crear departamento", 
			"Actualizar departamento", 
			"Mostrar rnanking", 
			"Crear incidencia."
	); 

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
			option = InputAsker.pedirIndice("Selecciona una opcion:", Arrays.asList("Login"), true);
			switch(option) {
				case 1: login(); break;
				case 0: System.out.println("Hasta la proxima!"); break;
				default: System.err.println("Opcion invalida");
			}
		} while(option != 0);
	}
	
	// @Bou
	private static void mainMenu() {
		int option;
		List<String> options = getOptionsList();
		do {
			option = InputAsker.pedirIndice("Selecciona una opcion: ", options, true);
			try {
				switch(option) {
					case 1: updateEmpleado(); break;
					case 2: solucionarIncidencia(); break;
					case 3: listarIncidencias(); break;
					case 4: register(); break;
					case 5: deleteEmpleado(); break;
					case 6: crearDepartamento(); break;
					case 7: updateDepartamento(); break;
					case 8: mostrarRanking(); break;
					case 9: crearIncidencia(); break;
					case 0: System.out.println("Hasta la proxima!"); break;
					default: System.err.println("Opcion invalida");
				}
			} catch (InvalidException e) {
				System.err.println(e.getMessage());
			}
		} while (option != 0);
	}
	
	private static List<String> getOptionsList() {
		List<String> options = new ArrayList<>();
		options.addAll(opcionesEmpleado);
		if (controller.getUsuarioLogeado().isJefe())
			options.addAll(opcionesJefe);
		return options;
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
	private static void updateDepartamento() throws InvalidException {
		checkJefe();
		DepartamentoDTO dep = controller.getUsuarioLogeado().getDepartamento();
		System.out.println("Estas editando el departamento: " + dep.getNombre());
		int opt;
		do {
			opt = InputAsker.pedirIndice("Que dato quieres editar?", Arrays.asList("Nombre", "Añadir empleado", "Quitar empleado"), true);
			switch (opt) {
				case 1: 
					dep.setNombre(InputAsker.askString("Introduce el nuevo nombre: "));
					controller.updateDepartamento(dep);
					break;
				case 2: addEmpleadoADepartamento(dep); break;
				case 3: removeEmpleadDeDepartamento(dep);
			}
		} while(opt != 0);
	}


	private static void removeEmpleadDeDepartamento(DepartamentoDTO dep) {
		List<EmpleadoDTO> emps = controller.getEmpleados(dep, true);
		EmpleadoDTO empleado = emps.get(InputAsker.pedirIndice("Introduce el empleado a quitar", emps, false));
		empleado.setDepartamento(null);
		controller.updateEmpleado(empleado);
	}


	private static void addEmpleadoADepartamento(DepartamentoDTO dep) {
		List<EmpleadoDTO> emps = controller.getEmpleados(dep, false);
		EmpleadoDTO empleado = emps.get(InputAsker.pedirIndice("Introduce el empleado a añadir", emps, false));
		empleado.setDepartamento(dep.getNombre());
		controller.updateEmpleado(empleado);
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
		// TODO poner el empleado como que es jefe y aÃ±adir al departamento
		// TODO Cuevas: todas las tildes y Ã± me salen raras, hay que aï¿½adir UTF-8
	}
	
	private static void checkJefe() throws InvalidException {
		if (controller.getUsuarioLogeado().isJefe())
			throw new InvalidException(Tipo.UNAUTHORIZED);
	}
}
