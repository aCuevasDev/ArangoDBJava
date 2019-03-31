package persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.arangodb.util.MapBuilder;
import exception.InvalidException;
import model.DepartamentoDTO;
import model.EmpleadoDTO;
import model.EventoDTO;
import model.IncidenciaDTO;
import model.RankingEntryDTO;

/**
 * Implementaci�n de la clase DAO, encargada de acceder, modificar y eliminar datos.
 * 
 * @author razz97
 * @author acuevas
 * @author movip88
 */
public class DAOImpl extends ArangoUtils implements DAO {

	private static DAOImpl instance;

	/**
	 * Gets the unique instance of this class.
	 */
	public static DAOImpl getInstance() {
		if (instance == null)
			instance = new DAOImpl();
		return instance;
	}

	private DAOImpl() {}

	
	@Override
	public void close() {
		super.close();
	}

	
	@Override
	public EmpleadoDTO getEmpleado(String username) {
		return getByKey(new EmpleadoDTO(username), EmpleadoDTO.class);
	}

	@Override
	public EmpleadoDTO getEmpleado(String username, String pass) {
		EmpleadoDTO emp = getByKey(new EmpleadoDTO(username), EmpleadoDTO.class);
		return emp == null || !emp.getContrasenya().equals(pass) ? null : emp;
	}

	@Override
	public List<EmpleadoDTO> getEmpleados() {
		return find(EmpleadoDTO.class);
	}
	
	@Override
	public List<EmpleadoDTO> getEmpleados(DepartamentoDTO dep, boolean inside) {
		if (!isCollection("empleadodto"))
			return new ArrayList<EmpleadoDTO>();
		String query = "for e in empleadodto filter e.departamento " + (inside ? "=" : "!") + "= \"" + dep.getKey() + "\" return e";
		return query(query, EmpleadoDTO.class);
	}

	@Override
	public void insertEmpleado(EmpleadoDTO e) throws InvalidException {
		if (exists(e))
			throw new InvalidException(InvalidException.Tipo.USER_EXISTS);
		store(e);
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
	public List<DepartamentoDTO> getDepartmentos() {
		return find(DepartamentoDTO.class);
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
	public IncidenciaDTO getIncidencia(int id) {
		return getByKey(new IncidenciaDTO(id), IncidenciaDTO.class);
	}

	@Override
	public List<IncidenciaDTO> getIncidencias() {
		return find(IncidenciaDTO.class);
	}

	@Override
	public List<IncidenciaDTO> getIncidencias(EmpleadoDTO e, boolean destino) {
		return find(IncidenciaDTO.class, new MapBuilder().put(destino ? "destino" : "origen", e.getUsername()).get());
	}	
	
	@Override
	public List<IncidenciaDTO> getIncidencias(DepartamentoDTO dep) {
		if (!isCollection("incidenciadto") || !isCollection("empleadodto"))
			return new ArrayList<IncidenciaDTO>();
		return query(
			"for e in empleadodto filter e.departamento == '"+ dep.getKey() + "' for i in incidenciadto filter i.destino == e._key return i",
			IncidenciaDTO.class
		);
	}
	
	@Override
	public void insertIncidencia(IncidenciaDTO i) {
		forceStore(i);
	}

	@Override
	public void updateIncidencia(IncidenciaDTO incidencia) {
		store(incidencia);
	}


	@Override
	public EventoDTO getUltimoInicioSesion(EmpleadoDTO emp) {
		Map<String, Object> filters = new MapBuilder().put("empleado", emp.getUsername()).put("tipo", model.EventoDTO.Tipo.LOGIN).get();
		return find(EventoDTO.class, filters).stream().sorted().findFirst().orElse(null);
	}
	
	@Override
	public void insertEvento(EventoDTO evento) {
		store(evento);
	}

	
	@Override
	public List<RankingEntryDTO> getRanking(DepartamentoDTO dep) {
		if (!isCollection("incidenciadto") || !isCollection("departamentodto") || !isCollection("eventodto"))
			return new ArrayList<RankingEntryDTO>();
		return query(
			"for e in empleadodto "
			+ "filter e.departamento == '" + dep.getKey() + "' "
					+ "for i in eventodto "
					+ "filter i.empleado == e._key && i.tipo == 'SOLUCION_INCIDENCIA' "
							+ "collect user = e._key with count into resueltas "
			+ "return {'nombre' : user, 'incidenciasResueltas' : resueltas}",
			RankingEntryDTO.class
		);
	}

}
