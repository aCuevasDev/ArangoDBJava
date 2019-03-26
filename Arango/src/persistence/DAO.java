package persistence;

import java.util.List;

import exception.InvalidException;
import model.Empleado;
import model.Incidencia;
import model.dto.DepartamentoDTO;
import model.dto.EmpleadoDTO;
import model.dto.EventoDTO;
import model.dto.IncidenciaDTO;
import model.dto.RankingDTO;

/**
 *
 * @author mfontana
 */
public interface DAO {

	// Método para insertar un nuevo empleado.
	public void insertEmpleado(EmpleadoDTO e) throws InvalidException;

	// Método para insertar un nuevo departamento.
	public void insertDepartamento(DepartamentoDTO d) throws InvalidException;

	// Método para modificar el perfil de un departamento.
	public void updateDepartamento(DepartamentoDTO d);

	// Método para validar el login de un empleado.
	public Empleado loginEmpleado(String user, String pass);

	// Método para modificar el perfil de un empleado.
	public void updateEmpleado(EmpleadoDTO empleado);

	// Método para eliminar un empleado.
	public void removeEmpleado(EmpleadoDTO e);

	// Obtener una Incidencia a partir de su Id.
	public Incidencia getIncidenciaById(int id);

	// Obtener una lista de todas las incidencias
	public List<IncidenciaDTO> selectAllIncidencias();
    // Obtener la lista de incidencias con destino un determinado
    // empleado, a partir de un objeto empleado.
    public List<IncidenciaDTO> getIncidenciaByDestino(EmpleadoDTO e);

	// Obtener una lista de todos los empleados
	public List<EmpleadoDTO> selectAllEmpleados();
    // Obtener la lista de incidencias con origen un determinado
    // empleado, a partir de un objeto empleado.
    public List<IncidenciaDTO> getIncidenciaByOrigen(EmpleadoDTO e);

	// Insertar una incidencia a partir de un objeto incidencia
	public void insertIncidencia(IncidenciaDTO i);

	// Método para insertar un evento en la tabla historial.
	// Pasaremos como parámetro un objeto tipo evento, y no devolverá nada.
	// Llamaremos a este método desde los métodos
	// que producen los eventos, que son 3:
	// 1) Cuando un usuario hace login
	// 2) Cuando un usuario crea una incidencia de tipo urgente
	// 3) Cuando se consultan las incidencias destinadas a un usuario
	public void insertarEvento(EventoDTO e);

	// Obtener la fecha-hora del último inicio de sesión para un empleado.
	public EventoDTO getUltimoInicioSesion(EmpleadoDTO e);

	// Obtener el ranking de los empleados por cantidad de incidencias
	// urgentes creadas (más incidencias urgentes primero).
	public List<RankingDTO> getRankingEmpleados();

	public void close();

	public EmpleadoDTO getEmpleado(String username, String pass);

	public Empleado initialize(EmpleadoDTO emp);

	public Incidencia initialize(IncidenciaDTO inc);

	public List<DepartamentoDTO> selectAllDepartments();

	public List<IncidenciaDTO> getIncidenciasByDepartamento(DepartamentoDTO dep);
	
    public List<EmpleadoDTO> getEmpleadosByDepartamento(DepartamentoDTO dep);

	public void updateIncidencia(IncidenciaDTO incidencia);

	public EmpleadoDTO getEmpleado(String username);

}