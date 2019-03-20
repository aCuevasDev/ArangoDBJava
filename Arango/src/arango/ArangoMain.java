package arango;

import java.util.List;
import java.util.Set;

import controller.Controller;
import model.Departamento;
import model.Empleado;
import model.Incidencia;
import model.dto.DepartamentoDTO;
import model.dto.EmpleadoDTO;
import model.dto.IncidenciaDTO;
import persistence.DAOImpl;

public class ArangoMain {

	/**
	 * <pre>
	 *  TODO Cuevas: No me gusta nada tener estos m�todos aqu�, yo los pondr�a en el Controller. Pero como quer�is vosotros que es pijada m�a. Tambi�n cambiar�a el nombre del paquete :*
	 * </pre>
	 */

	public static void main(String[] args) {
		try {
			Controller.getInstance().crearEmpleado(new EmpleadoDTO("emp", "emp", "emp", "emp", crearDepartamento().getNombre()));
			IncidenciaDTO incidencia = new IncidenciaDTO("origen", "destino", "Test1", "incidencia test");
			DAOImpl.getInstance().insertIncidencia(incidencia);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Controller.getInstance().closeConexion();
		}
	}


	public static void login() {
		String username = InputAsker.askString("Introduce tu username");
		String contrasenya = InputAsker.askString("Introduce tu contrasenya");
		if (Controller.getInstance().login(username, contrasenya)) {
			System.out.println("Bienvenido");
		} else {
			System.out.println("Usuario o contraseña incorrectos");
		}
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
