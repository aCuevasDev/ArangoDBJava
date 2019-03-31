package controller;

import java.util.List;
import java.util.stream.Collectors;

import exception.InvalidException;
import exception.InvalidException.Tipo;
import model.DepartamentoDTO;
import model.EmpleadoDTO;
import model.EventoDTO;
import model.IncidenciaDTO;
import model.RankingEntryDTO;
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
	 * Cierra la conexion con la base de datos.
	 */
	public void closeConexion() {
		dao.close();
	}
	
	
	/**
	 * @return el usuario logueado.
	 */
	public EmpleadoDTO getUsuarioLogeado() {
		return usuarioLogeado;
	}

	/**
	 * Reemplaza al usuario logueado
	 * 
	 * @param usuarioLogeado el nuevo usuario logueado.
	 */
	public void setUsuarioLogeado(EmpleadoDTO usuarioLogeado) {
		this.usuarioLogeado = usuarioLogeado;
	}
	
	/**
	 * @return boolean indicando si el usuario esta logueado.
	 */
	public boolean isUsuaroLogueado() {
		return usuarioLogeado != null;
	}
	
	/**
	 * Inicia la sesion de un usuario en la aplicacion.
	 * @param username a validar.
	 * @param contrasenya a validar.
	 * @throws InvalidException credenciales incorrectas.
	 */
	public void login(String username, String contrasenya) throws InvalidException {
		setUsuarioLogeado(dao.getEmpleado(username, contrasenya));
		if (isUsuaroLogueado())
			crearEvento(EventoDTO.Tipo.LOGIN, usuarioLogeado.getUsername());
		throw new InvalidException(Tipo.INVALID_CREDENTIALS);
	}
	
	
	/**
	 * @param username del usuario a buscar
	 * @return el usuario en question.
	 */
	public EmpleadoDTO getEmpleado(String username) {
		return dao.getEmpleado(username);
	}
	
	/**
	 * @return lista con todos los empleados.
	 */
	public List<EmpleadoDTO> getEmpleados() {
		return dao.getEmpleados();
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
	 * Crea un empleado.
	 * @param empleado a crear.
	 * @throws InvalidException si el nombre de usuario ya esta en uso.
	 */
	public void insertEmpleado(EmpleadoDTO empleado) throws InvalidException {
		dao.insertEmpleado(empleado);
		if(empleado.isJefe()) {
			dao.updateDepartamento(new DepartamentoDTO(empleado.getDepartamento(), empleado.getUsername()));
		}
	}

	/**
	 * Actualiza un empleado.
	 * @param empleado a actualizar.
	 */
	public void updateEmpleado(EmpleadoDTO empleado) {
		dao.updateEmpleado(empleado);
	}

	/**
	 * Elimina un empleado teniento en cuenta si es jefe de un departamento.
	 * @param emp empleado a eliminar.
	 */
	public void deleteEmpleado(EmpleadoDTO emp) {
		dao.removeEmpleado(emp);
		if (emp.isJefe())
			dao.updateDepartamento(new DepartamentoDTO(emp.getDepartamento()));
		if(emp.equals(usuarioLogeado))
			usuarioLogeado = null;
	}
	
	/**
	 * @return lista con todos los departamentos.
	 */
	public List<DepartamentoDTO> getDepartamentos() {
		return dao.getDepartmentos();
	}

	/**
	 * Crea un departamento.
	 * @param departamento a crear.
	 * @throws InvalidException 
	 */
	public void insertDepartamento(DepartamentoDTO departamento) throws InvalidException {
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
	 * Actualiza un departamento.
	 * @param dep departamento a actualizar.
	 */
	public void updateDepartamento(DepartamentoDTO dep) {
		dao.updateDepartamento(dep);
	}

	
	/**
	 * Devuelve una lista de incidencias las cuales tienen como destino a un empleado en especifico.
	 * @param emp empleado destino de las incidencias a devolver.
	 * @return lista de incidencias.
	 */
	public List<IncidenciaDTO> getIncidencias(EmpleadoDTO emp) {
		return dao.getIncidencias(emp, true);
	}
	
	/**
	 * Si el usuario logueado es jefe, devuelve una lista de incidencias de todo el departamento,
	 * sino, devuelve las incidencias cuyo destino sea el usuario logueado.
	 * @return lista de incidencias.
	 */
	public List<IncidenciaDTO> getIncidenciasUsuarioLogueado() {
		if (!usuarioLogeado.isJefe()) 
			return dao.getIncidencias(usuarioLogeado, true);
		return dao.getIncidencias(new DepartamentoDTO(usuarioLogeado.getDepartamento()));
	}

	/**
	 * @return lista de incidencias pendientes del usuario logueado.
	 */
	public List<IncidenciaDTO> getIncidenciasPendientesUsuarioLogueado() {
		return getIncidenciasUsuarioLogueado().stream()
				.filter((incidencia) -> !incidencia.isSolucionada())
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
	 * Actualiza una incidenca y, en el caso de que sea urgente, se crea un evento de tipo solucion incidencia.
	 * @param incidencia
	 */
	public void updateIncidencia(IncidenciaDTO incidencia) {
		dao.updateIncidencia(incidencia);
		if (incidencia.isUrgente() && incidencia.isSolucionada())
			crearEvento(EventoDTO.Tipo.SOLUCION_INCIDENCIA, incidencia.getDestino());
	}

	/**
	 * @return El ranking de los empleados de un departamento.
	 */
	public List<RankingEntryDTO> getRanking() {
		return dao.getRanking(new DepartamentoDTO(usuarioLogeado.getDepartamento())).stream().sorted().collect(Collectors.toList());
	}

	/**
	 * Crea de un evento.
	 * @param tipo Tipo del evento
	 * @param empleado empleado que lo esta creando.
	 */
	public void crearEvento(EventoDTO.Tipo tipo, String empleado) {
		dao.insertEvento(new EventoDTO(tipo, empleado));
	}

}
