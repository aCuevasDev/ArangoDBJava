package controller;

import java.util.List;
import java.util.stream.Collectors;

import exception.InvalidException;
import exception.InvalidException.Tipo;
import model.Empleado;
import model.dto.DepartamentoDTO;
import model.dto.EmpleadoDTO;
import model.dto.IncidenciaDTO;
import persistence.DAO;
import persistence.DAOImpl;

public class Controller {

	private static Controller instance;

	private Empleado usuarioLogeado;
	private DAO dao;

	/**
	 * Metodo que gestiona la unica instancia de la calse cuando se llama por
	 * primera vez se crea la instgancia y si ya existe un la devuelve
	 *
	 * @return
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

	public String login(String username, String contrasenya) throws InvalidException {
		usuarioLogeado = dao.loginEmpleado(username, contrasenya);
		if (usuarioLogeado != null)
			return "Usuario creado correctamente.";
		throw new InvalidException(Tipo.INVALID_CREDENTIALS);
	}

	public String crearDepartamento(DepartamentoDTO departamento) throws InvalidException {
		dao.insertDepartamento(departamento);
		return "Departamento guardado correctamente";
	}

	public String crearEmpleado(EmpleadoDTO empleado) throws InvalidException {
		dao.insertEmpleado(empleado);
		if(empleado.isJefe()) {
			dao.updateDepartamento(new DepartamentoDTO(empleado.getDepartamento(), empleado.getUsername()));
		}
		return "Empleado guardado correctamente";
	}

	public List<EmpleadoDTO> getAllUsers() {
		return dao.selectAllEmpleados();
	}

	public List<DepartamentoDTO> getAllDepartamentos() {
		return dao.selectAllDepartments();
	}

	public void closeConexion() {
		dao.close();
	}

	public void updateDepartamento(DepartamentoDTO dep) {
		dao.updateDepartamento(dep);
	}

	public void eliminarEmpleado(EmpleadoDTO emp) {
		dao.removeEmpleado(emp);
		if (emp.isJefe()) {
			dao.updateDepartamento(new DepartamentoDTO(emp.getDepartamento()));
		}
		if(emp.equals(new EmpleadoDTO(usuarioLogeado))) {
			usuarioLogeado = null;
		}
	}

	public List<DepartamentoDTO> getAllDepartments() {
		return dao.selectAllDepartments();
	}
	
	public List<IncidenciaDTO> getIncidenciasPorEmpleado(EmpleadoDTO emp){
		return dao.getIncidenciaByDestino(emp);
	}
	
	public Empleado getUsuarioLogeado() {
		return usuarioLogeado;
	}

	public List<EmpleadoDTO> getEmpleados(DepartamentoDTO dep, boolean inside) {
		return dao.selectEmpleados(dep, inside);
	}

	public void updateEmpleado(EmpleadoDTO empleado) {
		dao.updateEmpleado(empleado);
	}

	public void updateIncidencia(IncidenciaDTO incidencia) {
		dao.updateIncidencia(incidencia);
	}
	
	public List<IncidenciaDTO> getUserIncidencias() {
		if (!usuarioLogeado.isJefe()) 
			return dao.getIncidenciaByDestino(new EmpleadoDTO(usuarioLogeado));
		return dao.getIncidenciasByDepartamento(usuarioLogeado.getDepartamento());
	}

	public List<IncidenciaDTO> getUserIncidenciasNotSolved() {
		return getUserIncidencias().stream()
				.filter((incidencia) -> incidencia.getFechaFin() == null)
				.collect(Collectors.toList());
	}

	public void insertIncidencia(IncidenciaDTO incidenciaDTO) {
		//dao.insertIncidencia(incidenciaDTO);
		
	}

	public EmpleadoDTO getEmpleado(String username) {
		return dao.getEmpleado(username);
	}

	public boolean isUserLogged() {
		return usuarioLogeado != null;
	}

}
