package persistence;

import java.util.List;

import exception.InvalidException;
import model.DepartamentoDTO;
import model.EmpleadoDTO;
import model.EventoDTO;
import model.IncidenciaDTO;
import model.RankingEntryDTO;

/**
 * Define los metodos necesarios para acceder, modificar y eliminar datos.
 *
 * @author mfontana
 * @author razz97
 * @author acuevas
 * @author movip88
 */
public interface DAO {

	/**
	 * Metodo para cerrar la conexi�n a la base de datos.
	 */
	public void close();
	
	
	/**
	 *  Metodo para obtener a un empleado a partir de su username.
	 */
	public EmpleadoDTO getEmpleado(String username);
	
	/** 
	 * Método para validar el login de un empleado.
	 */
	public EmpleadoDTO getEmpleado(String user, String pass);
	
	/**
	 *  Obtener una lista de todos los empleados
	 */
	public List<EmpleadoDTO> getEmpleados();
	
	/**
	 *  Metodo para obtener los empleados de dentro/fuera de un departamento, dependiendo del booleano inside.
	 */
	public List<EmpleadoDTO> getEmpleados(DepartamentoDTO dep, boolean inside);
	
	/**
	 * Método para insertar un nuevo empleado.
	 */
	public void insertEmpleado(EmpleadoDTO e) throws InvalidException;
	
	/**
	 *  Método para modificar el perfil de un empleado.
	 */
	public void updateEmpleado(EmpleadoDTO empleado);
	
	/**
	 *  Método para eliminar un empleado.
	 */
	public void removeEmpleado(EmpleadoDTO e);

	
	/**
	 *  Metodo para obtener todos los departamentos.
	 */
	public List<DepartamentoDTO> getDepartmentos();
	
	/**
	 *  Método para insertar un nuevo departamento.
	 */
	public void insertDepartamento(DepartamentoDTO d) throws InvalidException;

	/**
	 *  Método para modificar el perfil de un departamento.
	 */
	public void updateDepartamento(DepartamentoDTO d);


	/**
	 *  Obtener una Incidencia a partir de su Id.
	 */
	public IncidenciaDTO getIncidencia(int id);

	/**
	 *  Obtener una lista de todas las incidencias
	 */
	public List<IncidenciaDTO> getIncidencias();
	
	/**
	 *  Metodo para obtener todas las incidencias de un departamento.
	 */
	public List<IncidenciaDTO> getIncidencias(DepartamentoDTO dep);
	
    /**
     *  Obtener la lista de incidencias con destino/origen un determinado
     *  empleado, a partir de un objeto empleado y el booleano indicando si es destino/origen.
     */
    public List<IncidenciaDTO> getIncidencias(EmpleadoDTO e, boolean destino);

	/**
	 *  Insertar una incidencia a partir de un objeto incidencia
	 */
	public void insertIncidencia(IncidenciaDTO i);

	/**
	 *  Metodo para actualizar una incidencia
	 */
	public void updateIncidencia(IncidenciaDTO incidencia);
	
	/**
	 *  Método para insertar un evento en la tabla historial.
	 *  Pasaremos como parámetro un objeto tipo evento, y no devolverá nada.
	 *  Llamaremos a este método desde los métodos
	 *  que producen los eventos, que son 3:
	 *  1) Cuando un usuario hace login
	 *  2) Cuando un usuario crea una incidencia de tipo urgente
	 *  3) Cuando se consultan las incidencias destinadas a un usuario 
	 */
	public void insertEvento(EventoDTO evento);

	/**
	 *  Obtener la fecha-hora del último inicio de sesión para un empleado.
	 */
	public EventoDTO getUltimoInicioSesion(EmpleadoDTO e);


	/**
	 *  Obtener el ranking de los empleados por cantidad de incidencias
	 *  urgentes solucionadas (más incidencias urgentes primero).
	 */
	public List<RankingEntryDTO> getRanking(DepartamentoDTO dep);
	
}