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

	private DAOImpl() {}

	@Override
	public void insertEmpleado(EmpleadoDTO e) {
		if (!exists(e))
			store(e);
		// TODO THROW EXCEPTION
	}

	@Override
	public boolean loginEmpleado(String username, String pass) {
		EmpleadoDTO emp = getEmpleado(username, pass);
		if (emp == null) return false;
		store(new EventoDTO(Tipo.LOGIN, emp.getUsername()));
		return true;
	}

	@Override
	public void updateEmpleado(EmpleadoDTO e) {
		store(e);
	}

	@Override
	public void removeEmpleado(Empleado e) {
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
		if (!exists(i))
			store(i);
		// else
		// TODO THROW ALREADY_EXISTS EXCEPTION

	}

	@Override
	public List<IncidenciaDTO> getIncidenciaByDestino(Empleado e) {
		Map<String, Object> filters = new MapBuilder().put("destino", e.getUsername()).get();
		return find(IncidenciaDTO.class, filters);
	}

	@Override
	public List<IncidenciaDTO> getIncidenciaByOrigen(Empleado e) {
		Map<String, Object> filters = new MapBuilder().put("origen", e.getUsername()).get();
		return find(IncidenciaDTO.class, filters);
	}

	@Override
	public void insertarEvento(EventoDTO e) {
		store(e);
	}

	@Override
	public EventoDTO getUltimoInicioSesion(EmpleadoDTO emp) {
		Map<String, Object> filters = new MapBuilder()
				.put("empleado", emp.getUsername())
				.put("tipo", Tipo.LOGIN)
				.get();
		return find(EventoDTO.class, filters)
				.stream()
				.sorted((event, other) -> event.getFecha().compareTo(other.getFecha()))
				.collect(Collectors.toList())
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
		if (!exists(d))
			store(d);
		throw new InvalidException(InvalidException.Tipo.DEPARTMENT_EXISTS);
	}

	@Override
	public void updateDepartamento(DepartamentoDTO d) {
		store(d);
	}

	@Override
	public List<EmpleadoDTO> selectAllEmpleados() {
		return find(EmpleadoDTO.class);
	}

	@Override
	public EmpleadoDTO getEmpleado(String username, String pass) {
		Map<String, Object> filters = new MapBuilder().put("username", username).put("contrasenya", pass).get();
		List<EmpleadoDTO> empleados = find(EmpleadoDTO.class, filters);
		if (empleados.isEmpty()) return null;
		return empleados.get(0);
	}

	@Override
	public Empleado initializeEmpleado(EmpleadoDTO emp) {
		List<DepartamentoDTO> departamentos = find(DepartamentoDTO.class, new MapBuilder().put("nombre", emp.getDepartamento()).get());
		//if (departamentos.isEmpty()) 
		// TODO Throw exception
		return new Empleado(emp, departamentos.get(0));
	}

	@Override
	public List<DepartamentoDTO> selectAllDepartments() {
		return find(DepartamentoDTO.class);
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
