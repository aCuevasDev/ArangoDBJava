package persistence;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.arangodb.util.MapBuilder;

import exception.InvalidException;
import model.Empleado;
import model.Evento.Tipo;
import model.Incidencia;
import model.dto.DepartamentoDTO;
import model.dto.EmpleadoDTO;
import model.dto.EventoDTO;
import model.dto.IncidenciaDTO;
import model.dto.RankingDTO;

public class DAOImpl extends ArangoUtils implements DAO {

	private static DAOImpl instance;

	public static DAOImpl getInstance() {
		if (instance == null)
			instance = new DAOImpl();
		return instance;
	}

	private DAOImpl() {
	}

	@Override
	public void insertEmpleado(EmpleadoDTO e) throws InvalidException {
		if (exists(e))
			throw new InvalidException(InvalidException.Tipo.USER_EXISTS);
		store(e);
	}

	@Override
	public Empleado loginEmpleado(String username, String pass) {
		EmpleadoDTO emp = getByKey(new EmpleadoDTO(username), EmpleadoDTO.class);
		if (emp == null || !emp.getContrasenya().equals(pass))
			return null;
		return initialize(emp);
	}
	
	@Override
	public void crearEvento(EventoDTO evento) {
		store(evento);
	}

	@Override
	public void updateEmpleado(EmpleadoDTO e) {
		store(e);
	}

	@Override
	public void removeEmpleado(EmpleadoDTO e) {
		delete(e);
	}

	@Override
	public Incidencia getIncidenciaById(int id) {
		Map<String, Object> filters = new MapBuilder().put("id", id).get();
		return find(Incidencia.class, filters).stream().findFirst().get();
	}

	@Override
	public List<IncidenciaDTO> selectAllIncidencias() {
		return find(IncidenciaDTO.class);
	}

	@Override
	public void insertIncidencia(IncidenciaDTO i) {
		forceStore(i);
	}

	@Override
	public List<IncidenciaDTO> getIncidenciaByDestino(EmpleadoDTO e) {
		Map<String, Object> filters = new MapBuilder().put("destino", e.getUsername()).get();
		return find(IncidenciaDTO.class, filters);
	}

	@Override
	public List<IncidenciaDTO> getIncidenciaByOrigen(EmpleadoDTO e) {
		Map<String, Object> filters = new MapBuilder().put("origen", e.getUsername()).get();
		return find(IncidenciaDTO.class, filters);
	}

	@Override
	public void insertarEvento(EventoDTO e) {
		store(e);
	}

	@Override
	public EventoDTO getUltimoInicioSesion(EmpleadoDTO emp) {
		Map<String, Object> filters = new MapBuilder().put("empleado", emp.getUsername()).put("tipo", Tipo.LOGIN).get();
		return find(EventoDTO.class, filters).stream()
				.sorted((event, other) -> event.getFecha().compareTo(other.getFecha())).collect(Collectors.toList())
				.get(0);
	}

	@Override
	public List<RankingDTO> getRankingEmpleados() {
//		return find(RankingDTO.class);
		return null;
	}

	@Override
	public void close() {
		super.close();
	}

	@Override
	public void insertDepartamento(DepartamentoDTO d) throws InvalidException {
		if (exists(d))
			throw new InvalidException(InvalidException.Tipo.DEPARTMENT_EXISTS);
		store(d);
	}

	@Override
	public void updateDepartamento(DepartamentoDTO d) {
		store(d);
	}

	@Override
	public List<EmpleadoDTO> selectAllEmpleados() {
		return find(EmpleadoDTO.class);
	}
	
	public List<EmpleadoDTO> selectEmpleados(DepartamentoDTO dep, boolean inside) {
		//Map<String, Object> filters = new MapBuilder().put("departamento", dep.getKey()).get();
		String query = "for e in empleadodto filter e.departamento " + (inside ? "=" : "!") + "= \"" + dep.getKey() + "\" return e";
		return query(query, EmpleadoDTO.class);
	}

	@Override
	public EmpleadoDTO getEmpleado(String username, String pass) {
		Map<String, Object> filters = new MapBuilder().put("username", username).put("contrasenya", pass).get();
		List<EmpleadoDTO> empleados = find(EmpleadoDTO.class, filters);
		if (empleados.isEmpty())
			return null;
		return empleados.get(0);
	}

	@Override
	public Empleado initialize(EmpleadoDTO emp) {
		DepartamentoDTO departamento = getByKey(new DepartamentoDTO(emp.getDepartamento()), DepartamentoDTO.class);
		return new Empleado(emp, departamento);
	}

	@Override
	public List<DepartamentoDTO> selectAllDepartments() {
		return find(DepartamentoDTO.class);
	}

	@Override
	public List<IncidenciaDTO> getIncidenciasByDepartamento(DepartamentoDTO dep) {
		return query(
			"for e in empleadodto filter e.departamento == \""+ dep.getKey() + "\" for i in incidenciadto filter i.destino == e._key return i",
			IncidenciaDTO.class
		);
	}

	@Override
	public Incidencia initialize(IncidenciaDTO inc) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateIncidencia(IncidenciaDTO incidencia) {
		store(incidencia);
	}

	@Override
	public EmpleadoDTO getEmpleado(String username) {
		return getByKey(new EmpleadoDTO(username), EmpleadoDTO.class);
	}

	@Override
	public List<EmpleadoDTO> selectBecarios() {
		Map<String, Object> filters = new MapBuilder().put("jefe", false).get();
		List<EmpleadoDTO> empleados = find(EmpleadoDTO.class, filters);
		if (empleados.isEmpty())
			return null;
		return empleados;
	}

//	public Departamento initializeDepartamento(DepartamentoDTO dep) {
//		List<EmpleadoDTO> empleados = find(EmpleadoDTO.class,new MapBuilder().put("username", dep.getJefe()).get());
//		
//		//if (departamentos.isEmpty()) 
//		// TODO Throw exception
//		
//		return new Departamento();
//	}

}
