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
import model.dto.IncidenciaDTO;
import persistence.DAOImpl;

public class ArangoMain {

	private static Controller controller;
	// @formatter:off
	// Estas listas no permiten anadir o quitar elementos on the fly
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
			"Mostrar ranking", 
			"Crear incidencia."
	); 
	// @formatter:on

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
			switch (option) {
			case 1:
				login();
				break;
			case 0:
				System.out.println("Hasta la proxima!");
				break;
			}
		} while (option != 0);
	}

	private static void mainMenu() {
		int option;
		List<String> options = getOptionsList();
		do {
			option = InputAsker.pedirIndice("Selecciona una opcion: ", options, true);
			try {
				switch (option) {
				case 1:
					updateEmpleado();
					break;
				case 2:
					solucionarIncidencia();
					break;
				case 3:
					controller.getUserIncidencias().forEach(System.out::println);
					break;
				case 4:
					register();
					break;
				case 5:
					deleteEmpleado();
					break;
				case 6:
					crearDepartamento();
					break;
				case 7:
					updateDepartamento();
					break;
				case 8:
					mostrarRanking();
					break;
				case 9:
					crearIncidencia();
					break;
				case 0:
					System.out.println("Hasta la proxima!");
					break;
				default:
					System.err.println("Opcion invalida");
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
	private static EmpleadoDTO register() throws InvalidException {
		checkJefe();
		// TODO Implementation (solo jefe)
		EmpleadoDTO newEmpleado = new EmpleadoDTO();
		newEmpleado.setNombre(InputAsker.askString("Introduce el nombre del empleado: "));
		newEmpleado.setApellidos(InputAsker.askString("Introduce los apellidos del empleado: "));
		newEmpleado.setUsername(InputAsker.askString("Introduce el username del empleado: "));
		newEmpleado.setContrasenya(InputAsker.askString("Introduce la contraseña del empleado: "));
		List<DepartamentoDTO> departamentos = Controller.getInstance().getAllDepartamentos();
		int eleccion = InputAsker.askElementList("Escoje el departamento", departamentos);
		if (eleccion == 0) {
			newEmpleado.setDepartamento(null);
		} else {
			newEmpleado.setDepartamento(departamentos.get(eleccion - 1).getNombre());
			newEmpleado.setJefe(departamentos.get(eleccion - 1).getJefe() == null);
		}
		System.out.println(Controller.getInstance().crearEmpleado(newEmpleado));
		return newEmpleado;
	}

	// @Cuevas
	private static void updateEmpleado() {
		Empleado usuariologueado = controller.getUsuarioLogeado();
		System.out.println("Editando tu perfil: ");
		int opt; // nombre, apellido, contraseña.
		do {
			opt = InputAsker.pedirIndice("Qué dato quieres editar?", Arrays.asList("Nombre", "Apellidos", "Contraseña"),
					true);
			String value = "";
			if (opt != 0)
				value = InputAsker.askString("Cambiarlo a: ");
			switch (opt) {
			case 1:
				usuariologueado.setNombre(value);
				break;
			case 2:
				usuariologueado.setApellidos(value);
				break;
			case 3:
				usuariologueado.setContrasenya(value);
			}
		} while (opt != 0);
		controller.updateEmpleado(new EmpleadoDTO(usuariologueado));
	}

	// @Bou
	private static void updateDepartamento() throws InvalidException {
		checkJefe();
		DepartamentoDTO dep = controller.getUsuarioLogeado().getDepartamento();
		System.out.println("Estás editando el departamento: " + dep.getNombre());
		int opt;
		do {
			opt = InputAsker.pedirIndice("Que dato quieres editar?",
					Arrays.asList("Anadir empleado", "Quitar empleado"), true);
			switch (opt) {
			case 1:
				addEmpleadoADepartamento(dep);
				break;
			case 2:
				removeEmpleadDeDepartamento(dep);
			}
		} while (opt != 0);
	}

	private static void removeEmpleadDeDepartamento(DepartamentoDTO dep) {
		List<EmpleadoDTO> emps = controller.getEmpleados(dep, true);
		EmpleadoDTO empleado = emps.get(InputAsker.pedirIndice("Introduce el empleado a quitar", emps, false) - 1);
		empleado.setDepartamento(null);
		controller.updateEmpleado(empleado);
	}

	private static void addEmpleadoADepartamento(DepartamentoDTO dep) {
		List<EmpleadoDTO> emps = controller.getEmpleados(dep, false);
		EmpleadoDTO empleado = emps.get(InputAsker.pedirIndice("Introduce el empleado a anadir", emps, false) - 1);
		empleado.setDepartamento(dep.getNombre());
		controller.updateEmpleado(empleado);
	}

	// @Vives
	private static void deleteEmpleado() throws InvalidException {
		checkJefe();
		// TODO implementation (solo jefe)
		List<EmpleadoDTO> empleados = Controller.getInstance().getAllUsers();
		EmpleadoDTO empleado = empleados
				.get(InputAsker.pedirIndice("Introduce el empleado a eliminar", empleados, false) - 1);
		controller.eliminarEmpleado(empleado);
	}

	// @Cuevas
	private static void solucionarIncidencia() throws InvalidException {
		checkJefe();

	}

	// @Vives
	private static void mostrarRanking() throws InvalidException {
		checkJefe();
		// TODO implementation solo jefe, incidencias urgentes 2 puntos otras 1 punto.
	}

	// @Cuevas
	private static void crearIncidencia() throws InvalidException {
		// TODO implementation (solo jefe)
		checkJefe();
		DAOImpl.getInstance().insertIncidencia(new IncidenciaDTO("emp", "emp", "test", "testest"));
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
		checkJefe();
		DepartamentoDTO newDepartamento = new DepartamentoDTO();
		newDepartamento.setNombre(InputAsker.askString("Introduce el nombre del departamento: "));
		List<EmpleadoDTO> empleados = Controller.getInstance().getAllUsers();
		int eleccion = InputAsker.askElementList("Escoje al jefe", empleados);
		newDepartamento.setJefe(eleccion == 0 ? null : empleados.get(eleccion - 1).getNombreCompleto());
		Controller.getInstance().crearDepartamento(newDepartamento);
		return newDepartamento;
		// TODO (Solo jefes)
		// TODO poner el empleado como que es jefe y añadir al departamento
		// TODO Cuevas: todas las tildes y ñ me salen raras, hay que a�adir UTF-8
		// TODO falta cambiar el estado del empleado a jefe y sacarlo del departamento
		// en el que estaba
	}

	private static void checkJefe() throws InvalidException {
		if (!controller.getUsuarioLogeado().isJefe())
			throw new InvalidException(Tipo.UNAUTHORIZED);
	}
}
