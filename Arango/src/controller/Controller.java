package controller;

import java.util.List;
import java.util.stream.Collectors;

import exception.InvalidException;
import exception.InvalidException.Tipo;
import model.DepartamentoDTO;
import model.EmpleadoDTO;
import model.EventoDTO;
import model.IncidenciaDTO;
import model.RankingDTO;
import persistence.DAO;
import persistence.DAOImpl;

/**
 * Clase que contiene la logica del programa.
 * 
 * @author razz97
 * @author acuevas
 * @author movip88
 */
public class Controller {

	private static Controller instance;

	private EmpleadoDTO usuarioLogeado;
	private DAO dao;

	/**
	 * Gets the unique instance of this class.
	 */
	public static Controller getInstance() {
		if (instance == null) {
			instance = new Controller();
		}
		return instance;
	}

	private Controller() {
		this.dao = DAOImpl.getInstance();
	}

	/**
	 * Inicia la sesion de un usuario en la aplicacion.
	 * @param username a validar.
	 * @param contrasenya a validar.
	 * @throws InvalidException credenciales incorrectas.
	 */
	public String login(String username, String contrasenya) throws InvalidException {
		usuarioLogeado = dao.getEmpleado(username, contrasenya);
		if (usuarioLogeado != null) {
			crearEvento(EventoDTO.Tipo.LOGIN, usuarioLogeado.getUsername());
			return "Usuario creado correctamente.";
		}
		throw new InvalidException(Tipo.INVALID_CREDENTIALS);
	}

	/**
	 * Crea un departamento.
	 * @param departamento a crear.
	 * @throws InvalidException 
	 */
	public void crearDepartamento(DepartamentoDTO departamento) throws InvalidException {
		if (departamento.getJefe() != null) {
			EmpleadoDTO empleado = dao.getEmpleado(departamento.getJefe());
			if (empleado.isJefe())
				dao.updateDepartamento(new DepartamentoDTO(empleado.getDepartamento()));
			else
				empleado.setJefe(true);
			empleado.setDepartamento(departamento.getNombre());
			dao.updateEmpleado(empleado);
		}
		dao.insertDepartamento(departamento);
	}

	/**
	 * Crea un empleado.
	 * @param empleado a crear.
	 * @throws InvalidException si el nombre de usuario ya esta en uso.
	 */
	public void crearEmpleado(EmpleadoDTO empleado) throws InvalidException {
		dao.insertEmpleado(empleado);
		if(empleado.isJefe()) {
			dao.updateDepartamento(new DepartamentoDTO(empleado.getDepartamento(), empleado.getUsername()));
		}
	}

	
	/**
	 * @return lista con todos los empleados.
	 */
	public List<EmpleadoDTO> getAllUsers() {
		return dao.getEmpleados();
	}

	/**
	 * @return lista con todos los departamentos.
	 */
	public List<DepartamentoDTO> getAllDepartamentos() {
		return dao.getDepartmentos();
	}

	/**
	 * Cierra la conexion con la base de datos.
	 */
	public void closeConexion() {
		dao.close();
	}

	/**
	 * Actualiza un departamento.
	 * @param dep departamento a actualizar.
	 */
	public void updateDepartamento(DepartamentoDTO dep) {
		dao.updateDepartamento(dep);
	}

	/**
	 * Elimina un empleado teniento en cuenta si es jefe de un departamento.
	 * @param emp empleado a eliminar.
	 */
	public void eliminarEmpleado(EmpleadoDTO emp) {
		dao.removeEmpleado(emp);
		if (emp.isJefe()) {
			dao.updateDepartamento(new DepartamentoDTO(emp.getDepartamento()));
		}
		if(emp.equals(usuarioLogeado)) {
			usuarioLogeado = null;
		}
	}

	/**
	 * @return lista con todos los departamentos.
	 */
	public List<DepartamentoDTO> getAllDepartments() {
		return dao.getDepartmentos();
	}
	
	/**
	 * Devuelve una lista de incidencias las cuales tienenc como destino a un empleado en especifico.
	 * @param emp empleado destino de las incidencias a devolver.
	 * @return lista de incidencias.
	 */
	public List<IncidenciaDTO> getIncidenciasPorEmpleado(EmpleadoDTO emp){
		return dao.getIncidencias(emp, true);
	}
	
	/**
	 * @return el usuario logueado.
	 */
	public EmpleadoDTO getUsuarioLogeado() {
		return usuarioLogeado;
	}

	/**
	 * Lista de empleados teniendo en cuenta el departamento al que pertenecen.
	 * @param dep departamento por el que filtrar los empleados.
	 * @param inside booleano indicando si se desean los empleados de dentro o fuera del departeamento especificado.
	 * @return lista de empleados.
	 */
	public List<EmpleadoDTO> getEmpleados(DepartamentoDTO dep, boolean inside) {
		return dao.getEmpleados(dep, inside);
	}

	/**
	 * Actualiza un empleado.
	 * @param empleado a actualizar.
	 */
	public void updateEmpleado(EmpleadoDTO empleado) {
		dao.updateEmpleado(empleado);
	}

	/**
	 * Actualiza una incidenca y, en el caso de que sea urgente, se crea un evento de tipo solucion incidencia.
	 * @param incidencia
	 */
	public void updateIncidencia(IncidenciaDTO incidencia) {
		dao.updateIncidencia(incidencia);
		if (incidencia.isUrgente())
			crearEvento(EventoDTO.Tipo.SOLUCION_INCIDENCIA, incidencia.getDestino());
	}
	
	/**
	 * Si el usuario logueado es jefe, devuelve una lista de incidencias de todo el departamento,
	 * sino, devuelve las incidencias cuyo destino sea el usuario logueado.
	 * @return lista de incidencias.
	 */
	public List<IncidenciaDTO> getUserIncidencias() {
		if (!usuarioLogeado.isJefe()) 
			return dao.getIncidencias(usuarioLogeado, true);
		return dao.getIncidencias(new DepartamentoDTO(usuarioLogeado.getDepartamento()));
	}

	/**
	 * @return lista de incidencias pendientes del usuario logueado.
	 */
	public List<IncidenciaDTO> getUserIncidenciasNotSolved() {
		return getUserIncidencias().stream()
				.filter((incidencia) -> incidencia.getFechaFin() == null)
				.collect(Collectors.toList());
	}

	/**
	 * Crea una incidencia.
	 * @param incidenciaDTO a crear.
	 */
	public void insertIncidencia(IncidenciaDTO incidenciaDTO) {
		dao.insertIncidencia(incidenciaDTO);
	}

	/**
	 * @param username del usuario a buscar
	 * @return el usuario en question.
	 */
	public EmpleadoDTO getEmpleado(String username) {
		return dao.getEmpleado(username);
	}

	/**
	 * @return boolean indicando si el usuario esta logueado.
	 */
	public boolean isUserLogged() {
		return usuarioLogeado != null;
	}

	/**
	 * Creacion de un evento.
	 * @param tipo Tipo del evento
	 * @param empleado empleado que lo esta creando.
	 */
	public void crearEvento(EventoDTO.Tipo tipo, String empleado) {
		dao.insertEvento(new EventoDTO(tipo, empleado));
	}

	/**
	 * @return El ranking de los empleados de un departamento.
	 */
	public List<RankingDTO> getRanking() {
		return dao.getRanking(new DepartamentoDTO(usuarioLogeado.getDepartamento())).stream().sorted().collect(Collectors.toList());
	}

	/**
	 * Reemplaza al usuario logueado
	 * 
	 * @param usuarioLogeado el nuevo usuario logueado.
	 */
	public void setUsuarioLogeado(EmpleadoDTO usuarioLogeado) {
		this.usuarioLogeado = usuarioLogeado;
	}

}
