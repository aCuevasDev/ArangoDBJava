package arango;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.context.annotation.Bean;

import com.arangodb.ArangoDBException;

import controller.Controller;
import exception.InvalidException;
import exception.InvalidException.Tipo;
import model.DepartamentoDTO;
import model.EmpleadoDTO;
import model.IncidenciaDTO;
import model.RankingEntryDTO;

public class ArangoMain {

	private static Controller controller;
	private static final List<String> opcionesEmpleado = Arrays.asList( 
			"Ver mi perfil",
			"Actualizar perfil.", 
			"Listar incidencias",
			"Solucionar incidencia."			
	); 
	private static final List<String> opcionesJefe = Arrays.asList(
			"Crear incidencia.",
			"Mostrar ranking del departamento",
			"Registrar empleado.", 
			"Borrar empleado.", 
			"Listar empleados del departamento.",
			"Crear departamento.", 
			"Actualizar departamento."			
	); 

	@Bean
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
				case 1: login(); break;
				case 0: 
					System.out.println("Hasta la proxima!"); 
					System.exit(0);
				break;
			}
		} while (option != 0);
	}

	private static void mainMenu() {
		int option;
		List<String> options = getOptionsList();
		do {
			if (controller.getUsuarioLogeado() == null)
				break;
			option = InputAsker.pedirIndice("Selecciona una opcion: ", options, true);
			try {
				switch (option) {
					case 1: System.out.println(controller.getUsuarioLogeado()); break;
					case 2: updateEmpleado(); break;
					case 3: mostrarIncidencias(); break;
					case 4: solucionarIncidencia(); break;
					case 5: crearIncidencia(); break;
					case 6: mostrarRanking(); break;
					case 7: register(); break;
					case 8: deleteEmpleado(); break;
					case 9: listarDepartamento(); break;
					case 10: crearDepartamento(); break;
					case 11: updateDepartamento(); break;
					case 0: System.out.println("AdiÃ³s!"); break;
					default: System.err.println("Opcion invalida");
				}
			} catch (InvalidException e) {
				System.err.println(e.getMessage());
			}
		} while (option != 0 && controller.isUsuaroLogueado());
	}

	private static void listarDepartamento() {
		System.out.println("------ Empleados ------");
		controller
			.getEmpleados(new DepartamentoDTO(controller.getUsuarioLogeado().getDepartamento()), true)
			.forEach(System.out::println);
		System.out.println("-----------------------");
	}

	private static void mostrarIncidencias() {
		System.out.println("----- Incidencias -----");
		controller.getIncidenciasUsuarioLogueado().forEach(System.out::println); 	
		System.out.println("-----------------------");
	}

	private static List<String> getOptionsList() {
		List<String> options = new ArrayList<>();
		options.addAll(opcionesEmpleado);
		if (controller.getUsuarioLogeado().isJefe())
			options.addAll(opcionesJefe);
		return options;
	}

	private static EmpleadoDTO register() throws InvalidException {
		checkJefe();
		EmpleadoDTO newEmpleado = new EmpleadoDTO();
		newEmpleado.setNombre(InputAsker.pedirString("Introduce el nombre del empleado: "));
		newEmpleado.setApellidos(InputAsker.pedirString("Introduce los apellidos del empleado: "));
		newEmpleado.setUsername(InputAsker.pedirString("Introduce el username del empleado: "));
		newEmpleado.setContrasenya(InputAsker.pedirString("Introduce la contraseÃ±a del empleado: "));
		List<DepartamentoDTO> departamentos = controller.getDepartamentos();
		int eleccion = InputAsker.pedirIndice("Escoje el departamento", departamentos, true);
		if (eleccion == 0) {
			newEmpleado.setDepartamento(null);
		} else {
			newEmpleado.setDepartamento(departamentos.get(eleccion - 1).getNombre());
			newEmpleado.setJefe(departamentos.get(eleccion - 1).getJefe() == null);
		}
		controller.insertEmpleado(newEmpleado);
		System.out.println("Empleado registrado correctamente.");
		return newEmpleado;
	}

	private static void updateEmpleado() {
		EmpleadoDTO usuariologueado = controller.getUsuarioLogeado();
		System.out.println("Editando tu perfil: ");
		int opt;
		do {
			opt = InputAsker.pedirIndice("QuÃ© dato quieres editar?", Arrays.asList("Nombre", "Apellidos", "ContraseÃ±a"),true);
			String value = "";
			if (opt != 0)
				value = InputAsker.pedirString("Cambiarlo a: ");
			switch (opt) {
				case 1: usuariologueado.setNombre(value); break;
				case 2: usuariologueado.setApellidos(value); break;
				case 3: usuariologueado.setContrasenya(value);
			}
		} while (opt != 0);
		controller.updateEmpleado(usuariologueado);
	}

	private static void updateDepartamento() throws InvalidException {
		checkJefe();
		List<DepartamentoDTO> deps = controller.getDepartamentos();
		DepartamentoDTO dep = deps.get(InputAsker.pedirIndice("Escoge el departamento: ", deps, false) -1);
		int opt;
		do {
			opt = InputAsker.pedirIndice("Que dato quieres editar?",
					Arrays.asList("AÃ±adir empleado", "Quitar empleado", "Cambiar jefe"), true);
			switch (opt) {
			case 1:
				addEmpleadoADepartamento(dep);
				break;
			case 2:
				removeEmpleadDeDepartamento(dep);
				break;
			case 3:
				cambiarJefe(dep);
			}
		} while (opt != 0);
	}

	private static void removeEmpleadDeDepartamento(DepartamentoDTO dep) {
		List<EmpleadoDTO> emps = controller.getEmpleados(dep, true);
		if (emps.isEmpty()) {
			System.err.println("No hay empleados en este departamento.");
			return;
		}
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

	private static void cambiarJefe(DepartamentoDTO dep) {
		List<EmpleadoDTO> emps = controller.getEmpleados(dep, true);
		if (emps.isEmpty()) {
			System.err.println("No hay empleados en este departamento.");
			return;
		}
		if (dep.getJefe() != null) {
			EmpleadoDTO emp = emps.stream().filter(e -> e.getUsername().equals(dep.getJefe())).findFirst().orElse(null);
			if (emp != null) {
				emp.setJefe(false);
				controller.updateEmpleado(emp);
			}
		}
		EmpleadoDTO empleado = emps.get(InputAsker.pedirIndice("Introduce el empleado que serÃ¡ el jefe", emps, false) -1);
		empleado.setJefe(true);
		controller.updateEmpleado(empleado);
		dep.setJefe(empleado.getUsername());
		controller.updateDepartamento(dep);
	}

	private static void deleteEmpleado() throws InvalidException {
		checkJefe();
		List<EmpleadoDTO> empleados = controller
				.getEmpleados(new DepartamentoDTO(controller.getUsuarioLogeado().getDepartamento()), true);
		EmpleadoDTO empleado = empleados
				.get(InputAsker.pedirIndice("Introduce el empleado a eliminar", empleados, false) - 1);
		List<IncidenciaDTO> incidencias = controller.getIncidencias(empleado);
		if (incidencias.size() > 0) {
			List<EmpleadoDTO> allEmpleados = controller.getEmpleados();
			allEmpleados.remove(empleado);
			int eleccion = InputAsker.pedirIndice(
					"Escoje al empleado que recibirÃ¡ todas la incidencias de : " + empleado.getNombreCompleto(),
					allEmpleados, false) - 1;
			incidencias.stream().forEach(i -> {
				i.setDestino(allEmpleados.get(eleccion).getUsername());
				controller.updateIncidencia(i);
			});
		}
		controller.deleteEmpleado(empleado);
	}

	private static void solucionarIncidencia() throws InvalidException {
		List<IncidenciaDTO> incidencias = controller.getIncidenciasPendientesUsuarioLogueado();
		if (!incidencias.isEmpty()) {
			int index = InputAsker.pedirIndice("QuÃ© incidencia quieres marcar como solucionada?", incidencias, false) - 1;
			IncidenciaDTO incidencia = incidencias.get(index);
			incidencia.setFechaFin(new Date());
			controller.updateIncidencia(incidencia);
		} else
			System.out.println("No hay incidencias por solucionar.");
	}

	private static void mostrarRanking() throws InvalidException {
		checkJefe();
		List<RankingEntryDTO> ranking = controller.getRanking();
		System.out.println("------- Ranking -------");
		for (int i = 0; i < ranking.size(); i++) {
			System.out.println((i + 1) + ". " + ranking.get(i).toString());
		}
		System.out.println("-----------------------");
	}

	private static void crearIncidencia() throws InvalidException {
		checkJefe();
		EmpleadoDTO usuarioLogueado = controller.getUsuarioLogeado();
		List<EmpleadoDTO> empleadosEnDepartamento = controller
				.getEmpleados(new DepartamentoDTO(usuarioLogueado.getDepartamento()), true);
		String titulo = InputAsker.pedirString("Introduce el tÃ­tulo: ");
		String desc = InputAsker.pedirString("Introduce la descripciÃ³n: ");
		boolean urgente = InputAsker.pedirSiONo("Es urgente?");
		int indexEmpleado = InputAsker.pedirIndice("CuÃ¡l es el usuario de destino?", empleadosEnDepartamento, false);
		String destino = empleadosEnDepartamento.get(indexEmpleado - 1).getUsername();
		String origen = usuarioLogueado.getUsername();

		IncidenciaDTO incidenciaDTO = new IncidenciaDTO(origen, destino, titulo, desc, urgente);

		controller.insertIncidencia(incidenciaDTO);
	}

	private static void login() {
		try {
			String username = InputAsker.pedirString("Introduce tu username");
			String contrasenya = InputAsker.pedirString("Introduce tu contrasenya");
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
		newDepartamento
				.setNombre(InputAsker.pedirString("Introduce el nombre del departamento: ").trim().replace(" ", "_"));
		List<EmpleadoDTO> empleados = controller.getEmpleados();
		int eleccion = InputAsker.pedirIndice("Escoje al jefe", empleados, true);
		newDepartamento.setJefe(eleccion == 0 ? null : empleados.get(eleccion - 1).getUsername());
		controller.insertDepartamento(newDepartamento);
		return newDepartamento;
	}

	private static void checkJefe() throws InvalidException {
		if (!controller.getUsuarioLogeado().isJefe())
			throw new InvalidException(Tipo.UNAUTHORIZED);
	}
}
